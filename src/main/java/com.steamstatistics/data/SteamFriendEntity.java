package com.steamstatistics.data;

import javax.persistence.*;

@Entity
@Table(name="steamfriend")
@IdClass(SteamFriendEntityKey.class)
public class SteamFriendEntity {

    @Id
    @Column(name="steamid", updatable = true, nullable = false)
    private long steamid;

    @Id
    @Column(name="steamfriendid", updatable = true, nullable = false)
    private long steamfriendid;

    @Column(name="personname")
    private String personaname;

    @Column(name="profileurl")
    private String  profileurl;

    @Column(name="avatar")
    private String avatar;

    @Column(name="avatarmedium")
    private String avatarmedium;

    @Column(name="avatarfull")
    private String avatarfull;

    @Column(name="personastate")
    private String personastate;

    @Column(name="communityvisibilitystate")
    private String communityvisibilitystate;

    @Column(name="profilestate")
    private String profilestate;

    @Column(name="lastlogoff")
    private long lastlogoff;

    @Column(name="loccountrycode", nullable = true)
    private String loccountrycode;

    @Column(name = "friendsince", nullable = true)
    private long friendsince;

    @Column(name="adddate")
    private long addDate;

    @Column(name="removedate")
    private long removeDate;

    public long getSteamid() {
        return steamid;
    }

    public void setSteamid(long steamid) {
        this.steamid = steamid;
    }

    public long getSteamfriendid() { return steamfriendid; }

    public void setSteamfriendid(long steamfriendid) {
        this.steamfriendid = steamfriendid;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public String getPersonastate() {
        return personastate;
    }

    public void setPersonastate(String personastate) {
        this.personastate = personastate;
    }

    public String getCommunityvisibilitystate() {
        return communityvisibilitystate;
    }

    public void setCommunityvisibilitystate(String communityvisibilitystate) { this.communityvisibilitystate = communityvisibilitystate; }

    public String getProfilestate() {
        return profilestate;
    }

    public void setProfilestate(String profilestate) {
        this.profilestate = profilestate;
    }

    public long getLastlogoff() {
        return lastlogoff;
    }

    public void setLastlogoff(long lastlogoff) {
        this.lastlogoff = lastlogoff;
    }

    public String getLoccountrycode() { return loccountrycode; }

    public void setLoccountrycode(String loccountrycode) { this.loccountrycode = loccountrycode; }

    public long getFriendsince() { return friendsince; }

    public void setFriendsince(long friendsince) { this.friendsince = friendsince; }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }

    public long getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(long removeDate) {
        this.removeDate = removeDate;
    }
}
