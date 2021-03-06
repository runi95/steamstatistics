package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamProfileToFriendEntity;
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
        return processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriends));
    }

    public Map<Long, SteamProfileToFriendEntity> processFriendsList(List<Map<String, Object>> friendslist, long steamProfileid) {
        Map<Long, SteamProfileToFriendEntity> mappedFriends = new HashMap<>();

        for(Map<String, Object> map : friendslist) {
            int friendSince = (int) map.get("friend_since");
            long steamid = Long.parseLong((String) map.get("steamid"));

            SteamProfileToFriendEntity steamProfileToFriendEntity = new SteamProfileToFriendEntity();
            steamProfileToFriendEntity.setFriendsince(friendSince);
            steamProfileToFriendEntity.setSteamprofileid(steamProfileid);
            steamProfileToFriendEntity.setSteamfriendid(steamid);

            mappedFriends.put(steamid, steamProfileToFriendEntity);
        }

        return mappedFriends;
    }

    public Map<Long, SteamFriendEntity> processSteamFriends(List<Map<String, Object>> friends, long steamid) {
        Map<Long, SteamFriendEntity> steamFriends = new HashMap<>();

        for (Map<String, Object> map : friends) {
            int friendSince = (int) map.get("friend_since");
            SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
            long steamFriendid = Long.parseLong((String) map.get("steamid"));
            steamFriendEntity.setSteamid(steamFriendid);
            //steamFriendEntity.setFriendsince(friendSince);

            steamFriends.put(steamFriendid, steamFriendEntity);
        }

        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        steamFriendEntity.setSteamid(steamid);

        steamFriends.put(steamid, steamFriendEntity);

        return steamFriends;
    }

    public Map<Long, SteamFriendEntity> processSteamProfiles(List<Map<String, Object>> profiles) {
        Map<Long, SteamFriendEntity> map = new HashMap<>();

        for(Map<String, Object> profileMap : profiles) {
            processSteamProfile(map, profileMap);
        }

        return map;
    }

    public void processSteamProfile(Map<Long, SteamFriendEntity> map, Map<String, Object> profile) {
        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        Long steamid = Long.parseLong((String) profile.get("steamid"));
        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setPersonaname((String) profile.get("personaname"));
        steamFriendEntity.setProfileurl((String) profile.get("profileurl"));
        steamFriendEntity.setAvatar((String) profile.get("avatar"));
        steamFriendEntity.setAvatarmedium((String) profile.get("avatarmedium"));
        steamFriendEntity.setAvatarfull((String) profile.get("avatarfull"));
        steamFriendEntity.setCommunityvisibilitystate(Integer.toString((int)profile.get("communityvisibilitystate")));
        Object lastlogoff = profile.get("lastlogoff");
        if(lastlogoff != null)
            steamFriendEntity.setLastlogoff((int)lastlogoff);
        Object primaryclanid = profile.get("primaryclanid");
        if(primaryclanid != null)
            steamFriendEntity.setPrimaryclanid((String)primaryclanid);
        Object timeCreated = profile.get("timecreated");
        if(timeCreated != null)
            steamFriendEntity.setTimecreated((int)timeCreated);
        //steamFriendEntity.setGameid((long) profile.get("gameid"));
        //steamFriendEntity.setGameserverip((String) profile.get("gameserverip"));
        //steamFriendEntity.setGameextrainfo((String) profile.get("gameextrainfo"));

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
        Object profileState = profile.get("profilestate");
        if(profileState != null) {
            steamFriendEntity.setProfilestate(Integer.toString((int)profileState));
        }
        String realName = (String)profile.get("realname");
        if(realName != null && !realName.isEmpty()) {
            steamFriendEntity.setRealname(realName);
        }
        String loccountryCode = (String) profile.get("loccountrycode");
        if(loccountryCode != null && !loccountryCode.isEmpty()) {
            steamFriendEntity.setLoccountrycode(loccountryCode);
        }

        String locstateCode = (String) profile.get("locstatecode");
        if(locstateCode != null && !locstateCode.isEmpty()) {
            steamFriendEntity.setLocstatecode(locstateCode);
        }

        steamFriendEntity.setPersonastate(onlineState);
        Object commentPermission = profile.get("commentpermission");
        steamFriendEntity.setCommentpermission(commentPermission != null);

        Object loccityid = profile.get("loccityid");
        if(loccityid != null)
            steamFriendEntity.setLoccityid((int) loccityid);
        map.put(steamid, steamFriendEntity);
    }

    public String getCountryNameFromISO(String alpha2code) {
        Locale locale = new Locale("", alpha2code);

        return locale.getDisplayCountry();
    }
}
