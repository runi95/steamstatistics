package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;

import java.util.List;

public class Frontpage {
    private int ruinedfriendships, bondedfriendships;
    private List<SteamFriendEntity> longestFriendship;

    public Frontpage(int ruinedfriendships, int bondedfriendships, List<SteamFriendEntity> longestFriendship) {
        this.ruinedfriendships = ruinedfriendships;
        this.bondedfriendships = bondedfriendships;
        this.longestFriendship = longestFriendship;
    }

    public int getRuinedfriendships() {
        return ruinedfriendships;
    }

    public int getBondedfriendships() {
        return bondedfriendships;
    }

    public List<SteamFriendEntity> getLongestFriendship() {
        return longestFriendship;
    }
}
