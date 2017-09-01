package com.steamstatistics.data;

import javax.persistence.*;

@Entity
@Table(name="steamfriend")
public class SteamFriendEntity {

    @Id
    @Column(name="steamid", nullable = false)
    private long steamid;

    @Column(name = "updatetime", nullable = false)
    private long updatetime;

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

    @Column(name="commentpermission")
    private boolean commentpermission;

    @Column(name="realname")
    private String realname;

    @Column(name="primaryclanid")
    private String primaryclanid;

    @Column(name="timecreated")
    private long timecreated;

    @Column(name="gameid")
    private long gameid; // Unused for now

    @Column(name="gameserverip")
    private String gameserverip; // Unused for now

    @Column(name="gameextrainfo")
    private String gameextrainfo; // Unused for now

    @Column(name="loccountrycode")
    private String loccountrycode;

    @Column(name="locstatecode")
    private String locstatecode;

    @Column(name="loccityid")
    private int loccityid;

    public long getSteamid() { return steamid; }

    public void setSteamid(long steamid) {
        this.steamid = steamid;
    }

    public long getUpdatetime() { return updatetime; }

    public void setUpdatetime(long updatetime) { this.updatetime = updatetime; }

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

    public boolean getCommentpermission() { return commentpermission; }

    public void setCommentpermission(boolean commentpermission) { this.commentpermission = commentpermission; }

    public String getRealname() { return realname; }

    public void setRealname(String realname) { this.realname = realname; }

    public String getPrimaryclanid() { return primaryclanid; }

    public void setPrimaryclanid(String primaryclanid) { this.primaryclanid = primaryclanid; }

    public long getTimecreated() { return timecreated; }

    public void setTimecreated(int timecreated) { this.timecreated = timecreated; }

    public long getGameid() { return gameid; }

    public void setGameid(long gameid) { this.gameid = gameid; }

    public String getGameserverip() { return gameserverip; }

    public void setGameserverip(String gameserverip) { this.gameserverip = gameserverip; }

    public String getGameextrainfo() { return gameextrainfo; }

    public void setGameextrainfo(String gameextrainfo) { this.gameextrainfo = gameextrainfo; }

    public String getLocstatecode() { return locstatecode; }

    public void setLocstatecode(String locstatecode) { this.locstatecode = locstatecode; }

    public int getLoccityid() { return loccityid; }

    public void setLoccityid(int loccityid) { this.loccityid = loccityid; }
}
