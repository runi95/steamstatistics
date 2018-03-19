package com.steamstatistics.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;

@Entity
@Table(name = "SteamProfileToFriendTable")
@IdClass(SteamProfileToFriendEntityKey.class)
public class SteamProfileToFriendEntity {

    @Id
    @Column(name = "steamprofileid", nullable = false)
    @JsonSerialize(using = ToStringSerializer.class)
    long steamprofileid;

    @Id
    @Column(name = "steamfriendid", nullable = false)
    @JsonSerialize(using = ToStringSerializer.class)
    long steamfriendid;

    @Column(name = "friendsince")
    @JsonSerialize(using = ToStringSerializer.class)
    long friendsince;

    @Column(name="removedate")
    @JsonSerialize(using = ToStringSerializer.class)
    private long removeDate;

    public long getSteamprofileid() { return steamprofileid; }
    public void setSteamprofileid(long steamprofileid) { this.steamprofileid = steamprofileid; }
    public long getSteamfriendid() { return steamfriendid; }
    public void setSteamfriendid(long steamfriendid) { this.steamfriendid = steamfriendid; }
    public long getFriendsince() { return friendsince; }
    public void setFriendsince(long friendsince) { this.friendsince = friendsince; }
    public long getRemoveDate() { return removeDate; }
    public void setRemoveDate(long removeDate) { this.removeDate = removeDate; }
}
