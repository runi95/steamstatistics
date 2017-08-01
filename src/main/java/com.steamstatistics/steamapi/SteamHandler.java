package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamFriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class SteamHandler {

    @Autowired
    TimeService timeService;

    @Autowired
    SteamAPICaller steamAPICaller;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Cacheable("friends")
    public Map<Long, SteamFriendEntity> getProfile(String apikey, long steamid) {
        Map<Long, SteamFriendEntity> steamFriends = processSteamFriends(steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), steamid), steamid);
        return processSteamProfiles(steamid, steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriends), steamFriends);
    }

    public Map<Long, SteamFriendEntity> processSteamFriends(List<Map<String, Object>> friends, long steamid) {
        Map<Long, SteamFriendEntity> steamFriends = new HashMap<>();

        for (Map<String, Object> map : friends) {
            int friendSince = (int) map.get("friend_since");
            SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
            steamFriendEntity.setSteamid(steamid);
            long steamFriendid = Long.parseLong((String) map.get("steamid"));
            steamFriendEntity.setSteamfriendid(steamFriendid);
            steamFriendEntity.setFriendsince(friendSince);

            steamFriends.put(steamFriendid, steamFriendEntity);
        }

        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setSteamfriendid(steamid);

        steamFriends.put(steamid, steamFriendEntity);

        return steamFriends;
    }

    public Map<Long, SteamFriendEntity> processSteamProfiles(Long steamid, List<Map<String, Object>> profiles, Map<Long, SteamFriendEntity> steamFriends) {
        for(Map<String, Object> map : profiles) {
            processSteamProfile(steamid, map, steamFriends);
        }

        return steamFriends;
    }

    public void processSteamProfile(Long steamid, Map<String, Object> profile, Map<Long, SteamFriendEntity> steamFriends) {
        Long steamFriendid = Long.parseLong((String) profile.get("steamid"));
        SteamFriendEntity steamFriendEntity = steamFriends.get(steamFriendid);

        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setSteamfriendid(steamFriendid);
        steamFriendEntity.setPersonaname((String) profile.get("personaname"));
        steamFriendEntity.setProfileurl((String) profile.get("profileurl"));
        steamFriendEntity.setAvatar((String) profile.get("avatar"));
        steamFriendEntity.setAvatarmedium((String) profile.get("avatarmedium"));
        steamFriendEntity.setAvatarfull((String) profile.get("avatarfull"));
        steamFriendEntity.setCommunityvisibilitystate(Integer.toString((int)profile.get("communityvisibilitystate")));
        steamFriendEntity.setProfilestate(Integer.toString((int)profile.get("profilestate")));
        steamFriendEntity.setLastlogoff((Integer) profile.get("lastlogoff"));
        steamFriendEntity.setLoccountrycode((String) profile.get("loccountrycode"));

        String onlineState = null;
        switch((int)profile.get("personastate")) {
            case 5:
            case 0:
                onlineState = "offline";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
                onlineState = "online";
                break;
        }
        steamFriendEntity.setPersonastate(onlineState);
    }

    public String getCountryNameFromISO(String alpha2code) {
        Locale locale = new Locale("", alpha2code);

        return locale.getDisplayCountry();
    }
}
