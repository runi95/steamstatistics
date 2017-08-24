package com.steamstatistics.backend;

public class Frontpage {
    private int registeredusers, ruinedfriendships, bondedfriendships;
    private LongestFriendship longestFriendship;

    public Frontpage(int registeredusers, int ruinedfriendships, int bondedfriendships, LongestFriendship longestFriendship) {
        this.registeredusers = registeredusers;
        this.ruinedfriendships = ruinedfriendships;
        this.bondedfriendships = bondedfriendships;
        this.longestFriendship = longestFriendship;
    }

    public int getRegisteredusers() { return registeredusers; }

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
