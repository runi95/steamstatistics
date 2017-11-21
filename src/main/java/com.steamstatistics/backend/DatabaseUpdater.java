package com.steamstatistics.backend;

import com.steamstatistics.data.*;
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
    SteamProfileService steamProfileService;

    @Autowired
    SteamProfileToFriendService steamProfileToFriendService;

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
            Iterable<SteamProfileEntity> getAllProfiles = steamProfileService.getAll();
            Iterator<SteamProfileEntity> profileIterator = getAllProfiles.iterator();

            while(profileIterator.hasNext()) {
                SteamProfileEntity profile = profileIterator.next();

                long lastUpdated = profile.getLastupdate();
                long yesterday = timeService.getYesterdayUnixTime();

                if(lastUpdated < yesterday) {
                    List<Map<String, Object>> friendsList = steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), profile.getSteamid());
                    Map<Long, SteamProfileToFriendEntity> processedFriendsList = steamHandler.processFriendsList(friendsList, profile.getSteamid());
                    profile.setLastupdate(timeService.getCurrentUnixTime());
                    steamProfileService.save(profile);
                    steamProfileToFriendService.saveAll(processedFriendsList.values());
                }
            }

            System.out.println("76561198058838893: " + steamFriendService.get(76561198058838893l));
            System.out.println("76561198034901438: " + steamFriendService.get(76561198034901438l));

            Iterable<SteamFriendEntity> getAllFriends = steamFriendService.getAll();
            Iterator<SteamFriendEntity> friendIterator = getAllFriends.iterator();

            List<Long> steamFriendsToUpdate = new ArrayList<>();
            int iterator = 0;
            while (friendIterator.hasNext()) {
                iterator++;
                SteamFriendEntity friend = friendIterator.next();

                long lastUpdated = friend.getUpdatetime();
                long yesterday = timeService.getYesterdayUnixTime();

                if (lastUpdated < yesterday) {
                    steamFriendsToUpdate.add(friend.getSteamid());
                }
            }

            System.out.println("iterator: " + iterator);
            System.out.println("steamFriendsToUpdate.size() " + steamFriendsToUpdate.size());

            List<Map<String, Object>> list = steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriendsToUpdate);
            System.out.println("list size " + list.size());
            Map<Long, SteamFriendEntity> mappedProfiles = steamHandler.processSteamProfiles(list);
            System.out.println("Saving " + mappedProfiles.values().size() + " entities to steamFriendService");
            steamFriendService.saveAll(mappedProfiles.values());

            try {
                sleep(threadSleepInSecondsTime * 1000);
            } catch (InterruptedException e) {

            }
        }
    }
}
