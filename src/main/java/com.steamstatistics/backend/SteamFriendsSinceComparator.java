package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;

import java.util.Comparator;

public class SteamFriendsSinceComparator implements Comparator<SteamFriendEntity> {

    @Override
    public int compare(SteamFriendEntity t1, SteamFriendEntity t2) {
        if(t1.getFriendsince() > t2.getFriendsince())
            return -1;
        else if (t2.getFriendsince() > t1.getFriendsince())
            return 1;
        else return 0;
    }
}
