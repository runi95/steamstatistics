package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;

import java.util.List;

public class Frontpage {
    private final int registeredusers, ruinedfriendships, bondedfriendships;
    private final LongestFriendship longestFriendship;
    private final List<SteamFriendEntity> donators;

    public Frontpage(int registeredusers, int ruinedfriendships, int bondedfriendships, LongestFriendship longestFriendship, List<SteamFriendEntity> donators) {
        this.registeredusers = registeredusers;
        this.ruinedfriendships = ruinedfriendships;
        this.bondedfriendships = bondedfriendships;
        this.longestFriendship = longestFriendship;
        this.donators = donators;
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

    public List<SteamFriendEntity> getDonators() { return donators; }
}
