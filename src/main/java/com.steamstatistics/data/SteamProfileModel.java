package com.steamstatistics.data;

public class SteamProfileModel {
    private String steamId, steamName, onlineState, stateMessage, privacyState, visibilityState, avatarIcon, avatarMedium, avatarFull;

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getSteamName() {
        return steamName;
    }

    public void setSteamName(String steamName) {
        this.steamName = steamName;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState;
    }

    public String getStateMessage() { return stateMessage; }

    public void setStateMessage(String stateMessage) { this.stateMessage = stateMessage; }

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

    public String getAvatarIcon() { return avatarIcon; }

    public void setAvatarIcon(String avatarIcon) { this.avatarIcon = avatarIcon; }

    public String getAvatarMedium() { return avatarMedium; }

    public void setAvatarMedium(String avatarMedium) { this.avatarMedium = avatarMedium; }

    public String getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(String avatarFull) {
        this.avatarFull = avatarFull;
    }
}
