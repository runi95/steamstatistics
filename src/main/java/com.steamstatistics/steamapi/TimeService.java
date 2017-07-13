package com.steamstatistics.steamapi;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeService {
    public static void main(String[] args) {
        TimeService timeService = new TimeService();
        System.out.println(timeService.getCurrentUnixTime());
        System.out.println(timeService.getLastMonthUnixTime());
        System.out.println(timeService.getLastWeekUnixTime());
    }

    public static final int dayInSeconds = 86400;

    public long getCurrentUnixTime() {
        return Instant.now().getEpochSecond();
    }

    public long getLastMonthUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date lastMonth = calendar.getTime();

        return (lastMonth.getTime() / 1000);
    }

    public long getLastWeekUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date lastWeek = calendar.getTime();

        return (lastWeek.getTime() / 1000);
    }
}
