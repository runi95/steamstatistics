package com.steamstatistics.steamapi;

public class SteamFriends {
    private int friendsGainedLastMonth, friendsGainedLastWeek;
    private String monthIndex, weekIndex;
    private String friendsStringList;

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

    public String getFriendsStringList() {
        return friendsStringList;
    }

    public void setFriendsStringList(String friendsStringList) {
        this.friendsStringList = friendsStringList;
    }
}
