package com.steamstatistics.data;

public class SteamProfileModel {
    private String steamid, steamName, privacyState, visibilityState, avatarFull;

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getSteamName() {
        return steamName;
    }

    public void setSteamName(String steamName) {
        this.steamName = steamName;
    }

    public String getPrivacyState() {
        return privacyState;
    }

    public void setPrivacyState(String privacyState) {
        this.privacyState = privacyState;
    }

    public String getVisibilityState() {
        return visibilityState;
    }

    public void setVisibilityState(String visibilityState) {
        this.visibilityState = visibilityState;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(String avatarFull) {
        this.avatarFull = avatarFull;
    }
}
