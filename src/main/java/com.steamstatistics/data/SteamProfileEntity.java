package com.steamstatistics.data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "steamprofiles")
public class SteamProfileEntity {
    @Id
    @Column(name = "steamid", nullable = false, updatable = true, unique = true)
    private long steamid;

    @Column(name = "authtoken", nullable = false, updatable = true, unique = true)
    private String authtoken;

    @Column(name = "updatedate", nullable = true, updatable = true)
    private Timestamp lastupdate;

    @ElementCollection
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "steamid"))
    private Set<SteamFriendEntity> steamfriends = new HashSet<>();

    public long getSteamid() {
        return steamid;
    }

    public void setSteamid(long steamid) {
        this.steamid = steamid;
    }

    public String getAuthtoken() { return authtoken; }

    public void setAuthtoken(String authtoken) { this.authtoken = authtoken; }

    public Set<SteamFriendEntity> getSteamfriends() {
        return steamfriends;
    }

    public void setSteamfriends(Set<SteamFriendEntity> steamfriends) {
        this.steamfriends = steamfriends;
    }

    public Timestamp getLastupdate() { return lastupdate; }

    public void setLastupdate(Timestamp lastupdate) { this.lastupdate = lastupdate; }
}
