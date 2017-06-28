package com.steamstatistics.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "steamprofiles")
public class SteamProfileEntity {
    @Id
    @Column(name = "steamid", nullable = false, updatable = true, unique = true)
    private long steamid;

    @Column(name = "steamurl", nullable = false, updatable = true, unique = true)
    private String steamurl;

    @ElementCollection
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "steamid"))
    private Set<SteamFriendEntity> steamfriends = new HashSet<>();

    public long getSteamid() {
        return steamid;
    }

    public void setSteamid(long steamid) {
        this.steamid = steamid;
    }

    public String getSteamurl() {
        return steamurl;
    }

    public void setSteamurl(String steamurl) {
        this.steamurl = steamurl;
    }

    public Set<SteamFriendEntity> getSteamfriends() {
        return steamfriends;
    }

    public void setSteamfriends(Set<SteamFriendEntity> steamfriends) {
        this.steamfriends = steamfriends;
    }
}
