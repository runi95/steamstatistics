package com.steamstatistics.steamapi;

import com.steamstatistics.data.SteamFriendEntity;

import java.util.Map;

public class SteamFriends {
    private int friendsGainedLastMonth, friendsGainedLastWeek;
    private String monthIndex, weekIndex;
    private Map<Long, SteamFriendEntity> friendsList;

    public int getFriendsGainedLastMonth() {
        return friendsGainedLastMonth;
    }

    public void setFriendsGainedLastMonth(int friendsGainedLastMonth) {
        this.friendsGainedLastMonth = friendsGainedLastMonth;
        if(this.friendsGainedLastMonth > 0)
            monthIndex = "positive";
        else if(this.friendsGainedLastMonth == 0)
            monthIndex = "neutral";
        else if(this.friendsGainedLastMonth < 0)
            monthIndex = "negative";
    }

    public int getFriendsGainedLastWeek() {
        return friendsGainedLastWeek;
    }

    public void setFriendsGainedLastWeek(int friendsGainedLastWeek) {
        this.friendsGainedLastWeek = friendsGainedLastWeek;
        if(this.friendsGainedLastWeek > 0)
            weekIndex = "positive";
        else if(this.friendsGainedLastWeek == 0)
            weekIndex = "neutral";
        else if(this.friendsGainedLastWeek < 0)
            weekIndex = "negative";
    }

    public String getMonthIndex() {
        return monthIndex;
    }

    public String getWeekIndex() {
        return weekIndex;
    }

    public Map<Long, SteamFriendEntity> getFriendsList() { return friendsList; }

    public void setFriendsList(Map<Long, SteamFriendEntity> friendsList) {
        this.friendsList = friendsList;
    }

    public void addToFriendsList(SteamFriendEntity steamFriendEntity) { friendsList.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity); }
}