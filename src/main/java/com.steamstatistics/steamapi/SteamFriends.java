package com.steamstatistics.steamapi;

import com.steamstatistics.backend.SteamFriendsSinceComparator;
import com.steamstatistics.data.SteamFriendEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class SteamFriends {
    private int friendsGainedLastMonth, friendsGainedLastWeek, maxLevel, minLevel, averageLevel;
    private String monthIndex, weekIndex;
    private Map<Long, SteamFriendEntity> friendsList = new HashMap<>();
    private SteamFriendEntity steamProfile;
    private SteamFriendEntity[] oldest, newest;
    private TreeSet<SteamFriendEntity> sortedFriendsSet = new TreeSet<>(new SteamFriendsSinceComparator());
    private Map<String, Integer> countryMap = new HashMap<>();

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

    public SteamFriendEntity[] getOldest() {
        if(oldest != null) {
            return oldest;
        } else {
            Iterator<SteamFriendEntity> iterator = sortedFriendsSet.descendingIterator();

            SteamFriendEntity[] steamFriendEntities = new SteamFriendEntity[5];
            int i = 0;
            while (i < 5 && iterator.hasNext()) {
                steamFriendEntities[i++] = iterator.next();
            }

            return steamFriendEntities;
        }
    }

    public SteamFriendEntity[] getNewest() {
        if (newest != null) {
            return newest;
        } else {
            Iterator<SteamFriendEntity> iterator = sortedFriendsSet.iterator();

            SteamFriendEntity[] steamFriendEntities = new SteamFriendEntity[5];
            int i = 0;
            while (i < 5 && iterator.hasNext()) {
                steamFriendEntities[i++] = iterator.next();
            }

            return steamFriendEntities;
        }
    }

    public void addCountryCode(String country) {
        if(country != null) {
            if(countryMap.containsKey(country)) {
                countryMap.put(country, countryMap.get(country) + 1);
            } else {
                countryMap.put(country, 1);
            }
        }
    }

    public Map<String, Integer> getCountryMap() { return countryMap; }

    public void setOldest(SteamFriendEntity[] steamFriendEntity) { oldest = steamFriendEntity; }

    public void setNewest(SteamFriendEntity[] steamFriendEntity) { newest = steamFriendEntity; }

    public int getMaxLevel() { return maxLevel; }

    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    public int getMinLevel() { return minLevel; }

    public void setMinLevel(int minLevel) { this.minLevel = minLevel; }

    public int getAverageLevel() { return averageLevel; }

    public void setAverageLevel(int averageLevel) { this.averageLevel = averageLevel; }
}