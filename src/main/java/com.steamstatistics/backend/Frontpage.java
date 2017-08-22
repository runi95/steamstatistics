package com.steamstatistics.backend;

public class Frontpage {
    private int ruinedfriendships, bondedfriendships;
    private LongestFriendship longestFriendship;

    public Frontpage(int ruinedfriendships, int bondedfriendships, LongestFriendship longestFriendship) {
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

    public LongestFriendship getLongestFriendship() {
        return longestFriendship;
    }
}
