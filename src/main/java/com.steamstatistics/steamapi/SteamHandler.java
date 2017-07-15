package com.steamstatistics.steamapi;

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
        String[] steamids = new String[friends.size() + 1];

        for (Map<String, Object> map : friends) {
            steamids[index++] = (String) map.get("steamid");
            int friendSince = (int) map.get("friend_since");
            if (friendSince > lastWeek) {
                gainedWeek++;
                gainedMonth++;
            } else if (friendSince > lastMonth) {
                gainedMonth++;
            }
        }
        steamids[index] = steamid;

        steamFriends.setFriendsList(steamids);
        steamFriends.setFriendsGainedLastMonth(gainedMonth);
        steamFriends.setFriendsGainedLastWeek(gainedWeek);

        return steamFriends;
    }

    public HashMap<String, SteamProfileModel> processSteamProfiles(List<Map<String, Object>> profiles) {
        HashMap<String, SteamProfileModel> hashMap = new HashMap<>();

        for(Map<String, Object> map : profiles) {
            SteamProfileModel steamProfileModel = processSteamProfile(map);
            hashMap.put(steamProfileModel.getSteamId(), steamProfileModel);
        }

        return hashMap;
    }

    public SteamProfileModel processSteamProfile(Map<String, Object> profile) {
        SteamProfileModel steamProfileModel = new SteamProfileModel();

        steamProfileModel.setSteamId((String) profile.get("steamid"));
        steamProfileModel.setSteamName((String) profile.get("personaname"));
        steamProfileModel.setAvatarIcon((String) profile.get("avatar"));
        steamProfileModel.setAvatarMedium((String) profile.get("avatarmedium"));
        steamProfileModel.setAvatarFull((String) profile.get("avatarfull"));
        steamProfileModel.setVisibilityState(Integer.toString((int)profile.get("communityvisibilitystate")));
        steamProfileModel.setPrivacyState(Integer.toString((int)profile.get("profilestate")));

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

        steamProfileModel.setOnlineState(onlineState);

        return steamProfileModel;
    }
}
