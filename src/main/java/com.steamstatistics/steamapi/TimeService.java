package com.steamstatistics.steamapi;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TimeService {
    public static void main(String[] args) {
        TimeService timeService = new TimeService();
        System.out.println(timeService.getCurrentUnixTime());
        System.out.println("Last Month in calendar: " + timeService.getLastMonthUnixTime());
        System.out.println("Last Wweek in calendar: " + timeService.getLastWeekUnixTime());
    }

    public static final int dayInSeconds = 86400;

    public long getCurrentUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public long getLastMonthUnixTime() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC).minusMonths(1);
        localDateTime.toEpochSecond(ZoneOffset.UTC);

        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public long getLastWeekUnixTime() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC).minusWeeks(1);
        localDateTime.toEpochSecond(ZoneOffset.UTC);

        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
