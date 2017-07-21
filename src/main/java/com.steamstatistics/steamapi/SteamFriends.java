package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamFriendsSinceComparator;
import com.steamstatistics.data.SteamFriendEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class SteamFriends {
    private int friendsGainedLastMonth, friendsGainedLastWeek;
    private String monthIndex, weekIndex;
    private Map<Long, SteamFriendEntity> friendsList = new HashMap<>();
    private SteamFriendEntity steamProfile;
    private TreeSet<SteamFriendEntity> sortedFriendsSet = new TreeSet<>(new SteamFriendsSinceComparator());

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

    public void addToFriendsList(SteamFriendEntity steamFriendEntity) { friendsList.put(steamFriendEntity.getSteamfriendid(), steamFriendEntity); }

    public void addToSortedSet(SteamFriendEntity steamFriendEntity) { sortedFriendsSet.add(steamFriendEntity); }

    public TreeSet<SteamFriendEntity> getSortedFriendsSet() { return sortedFriendsSet; }

    public SteamFriendEntity getSteamProfile() { return steamProfile; }

    public void setSteamProfile(SteamFriendEntity steamProfile) { this.steamProfile = steamProfile; }

    public SteamFriendEntity getOldest() {
        return sortedFriendsSet.last();
    }

    public SteamFriendEntity getNewest() {
        return sortedFriendsSet.first();
    }
}