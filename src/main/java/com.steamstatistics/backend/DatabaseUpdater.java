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
            //updateDB();

            LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(timeService.getCurrentUnixTime());
            System.out.println(localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth() + " " + localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond() + " INFO --- [\tDatabaseUpdater");
            Iterable<SteamProfileEntity> getAllProfiles = steamProfileService.getAll();
            Iterator<SteamProfileEntity> profileIterator = getAllProfiles.iterator();

            List<SteamProfileToFriendEntity> steamFriendsToCheck = new ArrayList<>();
            long yesterday = timeService.getYesterdayUnixTime();
            List<Long> steamFriendsToUpdate = steamFriendService.findAllByUpdatetimeLessThanEpochAndReturnAsId(yesterday);

            while(profileIterator.hasNext()) {
                SteamProfileEntity profile = profileIterator.next();

                long lastUpdated = profile.getLastupdate();

                if(lastUpdated < yesterday) {
                    List<Map<String, Object>> friendsList = steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), profile.getSteamid());
                    Map<Long, SteamProfileToFriendEntity> processedFriendsList = steamHandler.processFriendsList(friendsList, profile.getSteamid());
                    profile.setLastupdate(timeService.getCurrentUnixTime());
                    steamProfileService.save(profile);
                    Map<Long, SteamProfileToFriendEntity> updateFriendsList = steamProfileToFriendService.updateFriendsList(processedFriendsList, profile.getSteamid())[0];
                    updateFriendsList.values().forEach((x) -> steamFriendsToCheck.add(x));
                }
            }

            for(SteamProfileToFriendEntity s : steamFriendsToCheck) {
                SteamFriendEntity steamFriendEntity = steamFriendService.get(s.getSteamfriendid());
                if(steamFriendEntity == null)
                    steamFriendsToUpdate.add(s.getSteamfriendid());
            }

            List<Map<String, Object>> list = steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriendsToUpdate);
            Map<Long, SteamFriendEntity> mappedProfiles = steamHandler.processSteamProfiles(list);
            steamFriendService.saveAll(mappedProfiles.values());

            try {
                sleep(threadSleepInSecondsTime * 1000);
            } catch (InterruptedException e) {

            }
        }
    }

    // TODO: Figure out if DatabaseUpdater finally works so this method can be safely removed.
    /*
    private void updateDB() {
        List<SteamProfileToFriendEntity> templist = steamProfileToFriendService.getAll();
        List<Long> update = new ArrayList<>();
        for(SteamProfileToFriendEntity s : templist) {
            SteamFriendEntity test = steamFriendService.get(s.getSteamfriendid());
            if(test == null) {
                update.add(s.getSteamfriendid());
            }
        }

        List<Map<String, Object>> list = steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), update);
        Map<Long, SteamFriendEntity> mappedProfiles = steamHandler.processSteamProfiles(list);
        steamFriendService.saveAll(mappedProfiles.values());
    }
    */
}
