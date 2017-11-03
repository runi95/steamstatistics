package com.steamstatistics.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "steamprofiles")
public class SteamProfileEntity {
    @Id
    @Column(name = "steamid", nullable = false, unique = true)
    private long steamid;

    @Column(name = "updatedate")
    private long lastupdate;

    @Column(name = "creationdate", nullable = false)
    private long creationdate;

    @Column(name = "donationdate")
    private long donationdate;

    public long getSteamid() {
        return steamid;
    }

    public void setSteamid(long steamid) {
        this.steamid = steamid;
    }

    public long getLastupdate() { return lastupdate; }

    public void setLastupdate(long lastupdate) { this.lastupdate = lastupdate; }

    public long getCreationdate() { return creationdate; }

    public void setCreationdate(long creationdate) { this.creationdate = creationdate; }

    public long getDonationdate() { return donationdate; }

    public void setDonationdate(long donationdate) { this.donationdate = donationdate; }
}
