package com.steamstatistics.steamapi;

import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamFriendService;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SteamHandler {

    @Autowired
    TimeService timeService;

    @Cacheable("friends")
    public SteamFriends processSteamFriends(List<Map<String, Object>> friends, long steamid) {
        SteamFriends steamFriends = new SteamFriends();

        long lastMonth = timeService.getLastMonthUnixTime(), lastWeek = timeService.getLastWeekUnixTime();
        int gainedMonth = 0, gainedWeek = 0;

        for (Map<String, Object> map : friends) {
            int friendSince = (int) map.get("friend_since");
            SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
            steamFriendEntity.setSteamfriendid(Long.parseLong((String) map.get("steamid")));
            steamFriendEntity.setFriendsince(friendSince);
            steamFriends.addToFriendsList(steamFriendEntity);
            steamFriends.addToSortedSet(steamFriendEntity);

            if (friendSince > lastWeek) {
                gainedWeek++;
                gainedMonth++;
            } else if (friendSince > lastMonth) {
                gainedMonth++;
            }
        }

        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        steamFriendEntity.setSteamid(steamid);
        steamFriendEntity.setSteamfriendid(steamid);
        steamFriends.setSteamProfile(steamFriendEntity);
        steamFriends.addToFriendsList(steamFriendEntity);
        steamFriends.addToSortedSet(steamFriendEntity);

        steamFriends.setFriendsGainedLastMonth(gainedMonth);
        steamFriends.setFriendsGainedLastWeek(gainedWeek);

        return steamFriends;
    }

    @Cacheable("summaries")
    public void processSteamProfiles(Long steamid, List<Map<String, Object>> profiles, Map<Long, SteamFriendEntity> friendsList) {
        for(Map<String, Object> map : profiles) {
            processSteamProfile(steamid, map, friendsList);
        }
    }

    public void processSteamProfile(Long steamid, Map<String, Object> profile, Map<Long, SteamFriendEntity> friendsList) {
        Long steamFriendid = Long.parseLong((String) profile.get("steamid"));
        SteamFriendEntity steamFriendEntity = friendsList.get(steamFriendid);

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
}
