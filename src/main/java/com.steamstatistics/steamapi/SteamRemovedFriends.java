package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamFriendWithDateComparator;
import com.steamstatistics.data.SteamFriendWithDate;

import java.util.TreeSet;

public class SteamRemovedFriends {
    private int friendsLostLastMonth, friendsLostLastWeek;
    private String monthIndex, weekIndex;
    private TreeSet<SteamFriendWithDate> sortedFriendsSet = new TreeSet<>(new SteamFriendWithDateComparator());

    public void addSteamFriend(SteamFriendWithDate steamFriendWithDate) {
        sortedFriendsSet.add(steamFriendWithDate);
    }

    public void setFriendsLostLastMonth(int friendsLostLastMonth) {
        this.friendsLostLastMonth = friendsLostLastMonth;
        if(this.friendsLostLastMonth > 0)
            monthIndex = "negative";
        else if(this.friendsLostLastMonth == 0)
            monthIndex = "neutral";
        else if(this.friendsLostLastMonth < 0)
            monthIndex = "positive";
    }

    public void setFriendsLostLastWeek(int friendsLostLastWeek) {
        this.friendsLostLastWeek = friendsLostLastWeek;
        if(this.friendsLostLastWeek > 0)
            weekIndex = "negative";
        else if(this.friendsLostLastWeek == 0)
            weekIndex = "neutral";
        else if(this.friendsLostLastWeek < 0)
            weekIndex = "positive";
    }

    public int getFriendsLostLastMonth() {
        return friendsLostLastMonth;
    }

    public int getFriendsLostLastWeek() {
        return friendsLostLastWeek;
    }

    public String getMonthIndex() {
        return monthIndex;
    }

    public String getWeekIndex() {
        return weekIndex;
    }

    public TreeSet<SteamFriendWithDate> getSortedFriendsSet() {
        return sortedFriendsSet;
    }
}
