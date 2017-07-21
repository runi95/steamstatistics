package com.steamstatistics.steamapi;

import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamFriendService;
import com.steamstatistics.data.SteamProfileModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SteamHandler {
    TimeService timeService = new TimeService();

    public SteamFriends processSteamFriends(List<Map<String, Object>> friends, String steamid) {
        SteamFriends steamFriends = new SteamFriends();

        long lastMonth = timeService.getLastMonthUnixTime(), lastWeek = timeService.getLastWeekUnixTime();
        int gainedMonth = 0, gainedWeek = 0, index = 0;
        HashMap<Long, SteamFriendEntity> steamFriendsList = new HashMap<>();

        for (Map<String, Object> map : friends) {
            Long friendsteamid = Long.parseLong((String) map.get("steamid"));
            int friendSince = (int) map.get("friend_since");
            SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
            steamFriendEntity.setSteamfriendid(friendsteamid);
            steamFriendEntity.setFriendsince(friendSince);

            steamFriendsList.put(friendsteamid, steamFriendEntity);

            if (friendSince > lastWeek) {
                gainedWeek++;
                gainedMonth++;
            } else if (friendSince > lastMonth) {
                gainedMonth++;
            }
        }

        Long longsteamid = Long.parseLong(steamid);
        SteamFriendEntity steamFriendEntity = new SteamFriendEntity();
        steamFriendEntity.setSteamfriendid(longsteamid);

        steamFriendsList.put(longsteamid, steamFriendEntity);

        steamFriends.setFriendsList(steamFriendsList);
        steamFriends.setFriendsGainedLastMonth(gainedMonth);
        steamFriends.setFriendsGainedLastWeek(gainedWeek);
        steamFriends.setFriendsList(steamFriendsList);

        return steamFriends;
    }

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
