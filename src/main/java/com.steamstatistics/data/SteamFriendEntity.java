package com.steamstatistics.data;

import javax.persistence.Embeddable;

@Embeddable
public class SteamFriendEntity {

    private String name, url, avatarurl;
    private long addDate, removeDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
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
