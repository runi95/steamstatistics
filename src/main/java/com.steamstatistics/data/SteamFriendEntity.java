package com.steamstatistics.data;

import javax.persistence.Embeddable;

@Embeddable
public class SteamFriendEntity {

    private String steamid, personaname, profileurl, avatar, avatarmedium, avatarfull, personastate, communityvisibilitystate, profilestate, lastlogoff;
    private long addDate, removeDate;

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
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

    public void setCommunityvisibilitystate(String communityvisibilitystate) {
        this.communityvisibilitystate = communityvisibilitystate;
    }

    public String getProfilestate() {
        return profilestate;
    }

    public void setProfilestate(String profilestate) {
        this.profilestate = profilestate;
    }

    public String getLastlogoff() {
        return lastlogoff;
    }

    public void setLastlogoff(String lastlogoff) {
        this.lastlogoff = lastlogoff;
    }

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
