package com.steamstatistics.data;

import com.steamstatistics.steamapi.TimeService;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

public class SteamFriendWithDate {
    SteamFriendEntity steamFriendEntity;
    long epochSeconds;
    LocalDateTime localDateTime;
    String localDateTimeString;

    public SteamFriendWithDate(SteamFriendEntity steamFriendEntity, long epochSeconds, TimeService timeService) {
        this.steamFriendEntity = steamFriendEntity;
        this.epochSeconds = epochSeconds;
        this.localDateTime = timeService.getLocalDateTimeFromUnix(this.epochSeconds);
        String month = Integer.toString(localDateTime.getMonthValue()), day = Integer.toString(localDateTime.getDayOfMonth());
        if(month.length() == 1)
            month = "0" + month;
        if(day.length() == 1)
            day = "0" + day;
        this.localDateTimeString = day + "." + month + "." + localDateTime.getYear();
    }

    public SteamFriendEntity getSteamFriendEntity() {
        return steamFriendEntity;
    }

    public long getEpochSeconds() {
        return epochSeconds;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getLocalDateTimeString() {
        return localDateTimeString;
    }
}
