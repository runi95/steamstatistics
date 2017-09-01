package com.steamstatistics.data;

import javax.persistence.*;

@Entity
@Table(name = "steamtokens")
@IdClass(SteamAuthEntityKey.class)
public class SteamAuthEntity {
    @Id
    @Column(name = "steamid", nullable = false, unique = true)
    private long steamid;

    @Id
    @Column(name = "authtoken", nullable = false, unique = true)
    private String authtoken;

    public SteamAuthEntity(long steamid, String authtoken) {
        this.steamid = steamid;
        this.authtoken = authtoken;
    }

    public long getSteamid() {
        return steamid;
    }

    public String getAuthtoken() {
        return authtoken;
    }
}
