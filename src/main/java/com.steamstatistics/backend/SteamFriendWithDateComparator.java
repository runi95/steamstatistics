package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamFriendWithDate;

import java.util.Comparator;

public class SteamFriendWithDateComparator implements Comparator<SteamFriendWithDate> {

    @Override
    public int compare(SteamFriendWithDate t1, SteamFriendWithDate t2) {
        if(t1.getEpochSeconds() > t2.getEpochSeconds())
            return -1;
        else if (t2.getEpochSeconds() > t1.getEpochSeconds())
            return 1;
        else return 0;
    }
}
