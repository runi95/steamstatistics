package com.steamstatistics.steamapi;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public long getYesterdayUnixTime() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC).minusDays(1);
        localDateTime.toEpochSecond(ZoneOffset.UTC);

        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public LocalDateTime getLocalDateTimeFromUnix(long unix) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unix), ZoneOffset.UTC);

        return localDateTime;
    }

    public String prettifyDate(LocalDateTime localDateTime) {
        int day = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        return (day > 9 ? day : "0" + day) + "/" + (month > 9 ? month : "0" + month) + "/" + year;
    }

    public long[] getEveryMonthOfAYear() {
        long current = getCurrentUnixTime();
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        long[] times = new long[13];
        times[0] = currentDateTime.toEpochSecond(ZoneOffset.UTC);
        currentDateTime = currentDateTime.minusDays(currentDateTime.getDayOfMonth() - 1);
        times[1] = currentDateTime.toEpochSecond(ZoneOffset.UTC);

        for(int i = 2; i < 13; i++) {
            currentDateTime = currentDateTime.minusMonths(1);
            times[i] = currentDateTime.toEpochSecond(ZoneOffset.UTC);
        }

        return times;
    }
}
