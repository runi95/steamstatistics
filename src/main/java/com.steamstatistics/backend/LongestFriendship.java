package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;

public class LongestFriendship {
    private final SteamFriendEntity frienda, friendb;
    private final String friendDateAsString;

    public LongestFriendship(SteamFriendEntity frienda, SteamFriendEntity friendb, String friendDateAsString) {
        this.frienda = frienda;
        this.friendb = friendb;
        this.friendDateAsString = friendDateAsString;
    }

    public SteamFriendEntity getFrienda() {
        return frienda;
    }

    public SteamFriendEntity getFriendb() {
        return friendb;
    }

    public String getFriendDateAsString() {
        return friendDateAsString;
    }
}
