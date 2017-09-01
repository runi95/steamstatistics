package com.steamstatistics.backend;

import com.steamstatistics.data.SteamProfileToFriendEntity;

import java.util.Comparator;

public class SteamProfileToFriendsEntityComparator implements Comparator<SteamProfileToFriendEntity> {

    @Override
    public int compare(SteamProfileToFriendEntity t1, SteamProfileToFriendEntity t2) {
        if(t1.getFriendsince() > t2.getFriendsince())
            return -1;
        else if (t2.getFriendsince() > t1.getFriendsince())
            return 1;
        else return 0;
    }
}
