package com.steamstatistics.backend;

import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamFriendService;
import com.steamstatistics.steamapi.SteamAPICaller;
import com.steamstatistics.steamapi.SteamHandler;
import com.steamstatistics.steamapi.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseUpdater extends Thread {
    private static final int threadSleepInSecondsTime = 3600;  // Hourly
    private boolean updating = true;

    @Autowired
    SteamFriendService steamFriendService;

    @Autowired
    TimeService timeService;

    @Autowired
    SteamAPICaller steamAPICaller;

    @Autowired
    SteamHandler steamHandler;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    public boolean isUpdating() {
        return updating;
    }

    public void steUpdate(boolean updating) {
        this.updating = updating;
    }

    @PostConstruct
    public void postConstruct() {
        start();
    }

    @Override
    public void run() {
        while(updating) {
            LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(timeService.getCurrentUnixTime());
            System.out.println(localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth() + " " + localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond() + " INFO --- [\tDatabaseUpdater");
            Iterable<SteamFriendEntity> getAll = steamFriendService.getAll();
            Iterator<SteamFriendEntity> iterator = getAll.iterator();

            List<Long> steamFriendsToUpdate = new ArrayList<>();
            while (iterator.hasNext()) {
                SteamFriendEntity friend = iterator.next();

                long lastUpdated = friend.getUpdatetime();
                long yesterday = timeService.getYesterdayUnixTime();

                if (lastUpdated < yesterday) {
                    steamFriendsToUpdate.add(friend.getSteamid());
                }
            }

            Map<Long, SteamFriendEntity> mappedProfiles = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriendsToUpdate));
            System.out.println(mappedProfiles.values().size());
            System.out.println("76561198051185314? " + mappedProfiles.get(76561198051185314l));
            System.out.println("76561198103109121? " + mappedProfiles.get(76561198103109121l));
            steamFriendService.saveAll(mappedProfiles.values());

            try {
                sleep(threadSleepInSecondsTime * 1000);
            } catch (InterruptedException e) {

            }
        }
    }
}
