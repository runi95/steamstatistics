package com.steamstatistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.steamstatistics.backend.Frontpage;
import com.steamstatistics.backend.LongestFriendship;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.*;
import com.steamstatistics.steamapi.*;
import com.steamstatistics.userauth.SteamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class SteamRestController {

    @Autowired
    SteamProfileService steamProfileService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Autowired
    SteamFriendService steamFriendService;

    @Autowired
    SteamProfileToFriendService steamProfileToFriendService;

    @Autowired
    SteamAPICaller steamAPICaller;

    @Autowired
    SteamHandler steamHandler;

    @Autowired
    TimeService timeService;

    @RequestMapping("/getprofile")
    public String getProfile(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        if(steamid == null) {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }

        SteamProfileEntity steamProfile = steamProfileService.get(steamid);
        long currentTime = timeService.getCurrentUnixTime();

        Map<Long, SteamFriendEntity> mappedSteamFriends;
        if(steamProfile != null && steamProfile.getLastupdate() > (currentTime - 3600)) {
            List<Long> friendsSteamIdList = steamProfileToFriendService.getAllFriendsSteamIds(steamid);
            List<SteamFriendEntity> steamFriends = steamFriendService.getAllInList(friendsSteamIdList);
            mappedSteamFriends = new HashMap<>();
            steamFriends.forEach((friend) -> mappedSteamFriends.put(friend.getSteamid(), friend));
            mappedSteamFriends.put(steamid, steamFriendService.get(steamid));
        } else {
            List<Map<String, Object>> friendsList = steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), steamid);
            Map<Long, SteamProfileToFriendEntity> mappedFriends = steamHandler.processFriendsList(friendsList, steamid);
            Map<Long, SteamProfileToFriendEntity>[] addedAndRemovedFriends = steamProfileToFriendService.updateFriendsList(mappedFriends, steamid);
            steamProfileToFriendService.saveAll(addedAndRemovedFriends[0].values());
            steamProfileToFriendService.saveAll(addedAndRemovedFriends[1].values());
            SteamProfileToFriendEntity steamProfileToFriendEntity = new SteamProfileToFriendEntity();
            steamProfileToFriendEntity.setSteamfriendid(steamid);
            addedAndRemovedFriends[0].put(steamid, steamProfileToFriendEntity);
            List<Map<String, Object>> summariesList = steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), addedAndRemovedFriends[0]);
            mappedSteamFriends = steamHandler.processSteamProfiles(summariesList);
            steamFriendService.saveAll(mappedSteamFriends.values());

            if(steamProfile == null) {
                steamProfile = new SteamProfileEntity();
                steamProfile.setSteamid(steamid);
                steamProfile.setCreationdate(currentTime);
                steamProfile.setLastupdate(currentTime);
                steamProfileService.save(steamProfile);
            }
        }

        LocalDateTime localTimeDate = timeService.getLocalDateTimeFromUnix(steamProfile.getCreationdate());
        String jdate = (localTimeDate.getDayOfMonth() <= 9 ? "0" + localTimeDate.getDayOfMonth() : localTimeDate.getDayOfMonth()) + "/" + (localTimeDate.getMonthValue() <= 9 ? "0" + localTimeDate.getMonthValue() : localTimeDate.getMonthValue()) + "/" + localTimeDate.getYear();

        List<Object> list = new ArrayList<>();
        list.add(mappedSteamFriends.get(steamid));
        list.add(jdate);
        list.add(mappedSteamFriends);
//        list.add(steamProfile);

        return convertObjectToJson(new RestMessageModel("200", "getprofile", list));
    }

    @RequestMapping("/getremoved")
    public String getRemoved(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        if(steamid == null) {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }
        List<SteamProfileToFriendEntity> steamProfileToFriendEntities = steamProfileToFriendService.getRemovedFriends(steamid);
        List<Long> steamidList = new ArrayList<>();
        steamProfileToFriendEntities.forEach((s) -> steamidList.add(s.getSteamfriendid()));
        Map<Long, SteamFriendEntity> removedFriends = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamidList));
        List<SteamFriendWithDate> removedFriendsWithDate = new ArrayList<>();
        for(SteamProfileToFriendEntity steamProfileToFriendEntity : steamProfileToFriendEntities) {
            SteamFriendWithDate steamFriendWithDate = new SteamFriendWithDate(removedFriends.get(steamProfileToFriendEntity.getSteamfriendid()), steamProfileToFriendEntity.getRemoveDate(), timeService);
            removedFriendsWithDate.add(steamFriendWithDate);
        }

        long lastMonth = timeService.getLastMonthUnixTime();

        List<Object> list = new ArrayList<>();
        list.add(removedFriendsWithDate);
        list.add(steamProfileToFriendService.findByRemoveDateGreaterThanAndProfileid(lastMonth, steamid).size());

        return convertObjectToJson(new RestMessageModel("200", "getremoved", list));
    }

    @RequestMapping("/getadded")
    public String getAdded(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        if(steamid == null) {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }
        List<SteamProfileToFriendEntity> steamProfileToFriendEntities = steamProfileToFriendService.findAllAddedFriends(steamid);
        List<Long> steamidList = new ArrayList<>();
        steamProfileToFriendEntities.forEach((s) -> steamidList.add(s.getSteamfriendid()));
        Map<Long, SteamFriendEntity> addedFriends = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamidList));
        List<SteamFriendWithDate> addedFriendsWithDate = new ArrayList<>();
        for(SteamProfileToFriendEntity steamProfileToFriendEntity : steamProfileToFriendEntities) {
            SteamFriendWithDate steamFriendWithDate = new SteamFriendWithDate(addedFriends.get(steamProfileToFriendEntity.getSteamfriendid()), steamProfileToFriendEntity.getFriendsince(), timeService);
            addedFriendsWithDate.add(steamFriendWithDate);
        }

        long lastMonth = timeService.getLastMonthUnixTime();

        List<Object> list = new ArrayList<>();
        list.add(addedFriendsWithDate);
        list.add(steamProfileToFriendService.findByFriendsinceGreaterThanAndProfileid(lastMonth, steamid).size());

        return convertObjectToJson(new RestMessageModel("200", "getadded", list));
    }

    @RequestMapping("/getfriends")
    public String getFriends(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        if(steamid == null) {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }

        List<SteamProfileToFriendEntity> steamProfileToFriendEntities = steamProfileToFriendService.getUnremovedFriends(steamid);
        List<Long> steamFriendidList = new ArrayList<>();
        steamProfileToFriendEntities.forEach((k) -> steamFriendidList.add(k.getSteamfriendid()));
        List<SteamFriendEntity> steamFriendsList = steamFriendService.getAllInList(steamFriendidList);
        Map<Long, SteamFriendEntity> steamFriends = new HashMap<>();
        steamFriendsList.forEach((f) -> steamFriends.put(f.getSteamid(), f));
//        List<SteamFriendWithDate> friendsWithDate = new ArrayList<>();
        List<SteamFriendWithDate> friendsWithDate = new ArrayList<>();
        for(SteamProfileToFriendEntity steamProfileToFriendEntity : steamProfileToFriendEntities) {
            //LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(steamProfileToFriendEntity.getRemoveDate());
            //String localDateTimeString = (localDateTime.getDayOfMonth() <= 9 ? "0" + localDateTime.getDayOfMonth() : localDateTime.getDayOfMonth()) + "/" + (localDateTime.getMonthValue() <= 9 ? "0" + localDateTime.getMonthValue() : localDateTime.getMonthValue()) + "/" + localDateTime.getYear();
            SteamFriendWithDate steamFriendWithDate = new SteamFriendWithDate(steamFriends.get(steamProfileToFriendEntity.getSteamfriendid()), steamProfileToFriendEntity.getFriendsince(), timeService);
            friendsWithDate.add(steamFriendWithDate);
        }
        //System.out.println("unremoved: " + steamProfileToFriendEntities.size() + ", steamFriendidList: " + steamFriendidList.size() + ", steamFriendsList: " + steamFriendsList.size() + ", steamFriends: " + steamFriends.size() + ", friendsWithDate: " + friendsWithDate.size());

        Collections.sort(friendsWithDate);

//        Map<Long, SteamFriendEntity> steamFriends = steamHandler.getProfile(steamOpenIdConfig.getClientSecret(), steamid);

        Map<String, Object> map = new HashMap<>();
        map.put("friends", friendsWithDate);
        map.put("length", friendsWithDate.size());
        return convertObjectToJson(new RestMessageModel("200", "getfriends", map));
    }

    @RequestMapping("/getfrontpage")
    public String getFrontpage() {
        List<SteamProfileToFriendEntity> steamProfileToFriendOrderedByFriendsinceDate = steamProfileToFriendService.getSteamProfileToFriendOrderedByFriendsinceDate();

        //LongestFriendship longestFriendship = steamProfileToFriendService.getLongestFriendship();
        SteamProfileToFriendEntity longestFriends = steamProfileToFriendService.getLongestFriendship();
        List<SteamProfileToFriendEntity> ruinedFriendshipsList = steamProfileToFriendService.findByRemoveDateGreaterThan(timeService.getLastMonthUnixTime());
        List<SteamProfileToFriendEntity> bondedFriendshipsList = steamProfileToFriendService.findByFriendsinceGreaterThan(timeService.getLastMonthUnixTime());
        List<SteamProfileEntity> steamProfileEntities = steamProfileService.findSortedDonationsList();
        List<SteamFriendEntity> donationsList = new ArrayList<>();
        List<Long> donatorSteamIDs = new ArrayList<>();
        for(int i = 0; i < steamProfileEntities.size() && i < 5; i++) {
            donatorSteamIDs.add(steamProfileEntities.get(i).getSteamid());
        }

        Map<Long, SteamFriendEntity> longestFriendshipMap = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), longestFriends.getSteamprofileid() + "," + longestFriends.getSteamfriendid()));

        LocalDateTime localTimeDate = timeService.getLocalDateTimeFromUnix(longestFriends.getFriendsince());
        LongestFriendship longestFriendship = new LongestFriendship(longestFriendshipMap.get(longestFriends.getSteamprofileid()), longestFriendshipMap.get(longestFriends.getSteamfriendid()), (localTimeDate.getDayOfMonth() <= 9 ? "0" + localTimeDate.getDayOfMonth() : localTimeDate.getDayOfMonth()) + "/" + (localTimeDate.getMonthValue() <= 9 ? "0" + localTimeDate.getMonthValue() : localTimeDate.getMonthValue()) + "/" + localTimeDate.getYear());

        Map<Long, SteamFriendEntity> donatorMap = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), donatorSteamIDs));
        for(Long key : donatorMap.keySet()) {
            donationsList.add(donatorMap.get(key));
        }

        int registeredUsers = steamProfileService.findByCreationdateGreaterThanEpoch(timeService.getLastMonthUnixTime()).size();
        //List<SteamProfileToFriendEntity> registeredUsersList = steamProfileToFriendService.getAll();
        int ruinedFriendships = ruinedFriendshipsList.size(), bondedFriendships = bondedFriendshipsList.size();

        Frontpage frontpage = new Frontpage(registeredUsers, ruinedFriendships, bondedFriendships, longestFriendship, donationsList);

        return convertObjectToJson(new RestMessageModel("200", "getfrontpage", frontpage));
    }

    @RequestMapping("/getfrontpage2")
    public String getFrontpageTwo() {
        List<Map<String, Object>> topthreelongestfriendships = new ArrayList();

        Object[][] longestFriendshipSortedArray = steamProfileToFriendService.getSteamProfileToFriendOrderedByFriendsinceDateTwo();
        for(int i = 0; i < 3 && i < longestFriendshipSortedArray.length; i++) {
            long steamprofileid = (long)longestFriendshipSortedArray[i][0];
            long friendshipepoch = (long)longestFriendshipSortedArray[i][1];

            SteamFriendEntity steamFriendEntity = steamFriendService.get(steamprofileid);

            LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(timeService.getCurrentUnixTime());
            LocalDateTime friendshipepochLocalDateTime = timeService.getLocalDateTimeFromUnix(friendshipepoch);

            localDateTime = localDateTime.minusDays(friendshipepochLocalDateTime.getDayOfMonth()).minusMonths(friendshipepochLocalDateTime.getMonthValue()).minusYears(friendshipepochLocalDateTime.getYear());
            int years = localDateTime.getYear(), months = localDateTime.getMonthValue(), days = localDateTime.getDayOfMonth();
            String prettifiedYears = (years == 0 ? "" : (years == 1) ? years + " year, " : years + " years, ");
            String prettifiedMonths = (months == 0 ? "" : (months == 1) ? months + " month and " : months + " months and ");
            String prettifiedDays = (days == 1 ? days + " day" : days + " days");
            String prettifiedFriendshipLengthText = prettifiedYears + prettifiedMonths + prettifiedDays;

            Map<String, Object> javascriptObject = new HashMap<>();
            javascriptObject.put("steamfriend", steamFriendEntity);
            javascriptObject.put("friendshipdurationdate", prettifiedFriendshipLengthText);

            topthreelongestfriendships.add(javascriptObject);
        }

        long lastMonth = timeService.getLastMonthUnixTime();

        /*
        List<SteamProfileToFriendEntity> lastMonthGainedFriendsList = steamProfileToFriendService.findByFriendsinceGreaterThan(lastMonth);
        Map<Long, Integer> lastMonthGainedFriendsCounterMap = new HashMap<>();
        long biggestFriendHoarderSteamId = 0;
        int biggestFriendHoarderCount = 0;
        for(SteamProfileToFriendEntity sptfe : lastMonthGainedFriendsList) {
            long steamprofileid = sptfe.getSteamprofileid();
            int currentCount;
            Integer count = lastMonthGainedFriendsCounterMap.get(steamprofileid);
            if(count == null)
                currentCount = 0;
            else
                currentCount = count;

            currentCount++;

            if(currentCount > biggestFriendHoarderCount) {
                biggestFriendHoarderCount = currentCount;
                biggestFriendHoarderSteamId = steamprofileid;
            }

            lastMonthGainedFriendsCounterMap.put(steamprofileid, currentCount);
        }

        SteamFriendEntity biggestHoarderFriendEntity = steamFriendService.get(biggestFriendHoarderSteamId);
        Map<String, Object> hoarderMap = new HashMap<>();
        hoarderMap.put("steamfriend", biggestHoarderFriendEntity);
        hoarderMap.put("count", biggestFriendHoarderCount);
        */

        List<Map<String, Object>> topThreeMonthlyHoardersList = new ArrayList<>();
        int totalHoardCount = 0;

        Object[][] lastMonthGainedFriendsList = steamProfileToFriendService.findByFriendsinceGreaterThanTwo(lastMonth);
        for(int i = 0; i < 3 && i < lastMonthGainedFriendsList.length; i++) {
            long steamprofileid = (long)lastMonthGainedFriendsList[i][0];
            long hoardcounter = (long)lastMonthGainedFriendsList[i][1];

            SteamFriendEntity steamFriendEntity = steamFriendService.get(steamprofileid);

            Map<String, Object> hoarderMap = new HashMap<>();
            hoarderMap.put("steamfriend", steamFriendEntity);
            hoarderMap.put("cnt", hoardcounter);

            topThreeMonthlyHoardersList.add(hoarderMap);

            totalHoardCount += hoardcounter;
        }

        List<Map<String, Object>> topThreeHoardersList = new ArrayList<>();

        Object[][] totalGainedFriendsList = steamProfileToFriendService.findByFriendsinceGreaterThanTwo(0);
        for(int i = 0; i < 3 && i < totalGainedFriendsList.length; i++) {
            long steamprofileid = (long)totalGainedFriendsList[i][0];
            long hoardcounter = (long)totalGainedFriendsList[i][1];

            SteamFriendEntity steamFriendEntity = steamFriendService.get(steamprofileid);

            Map<String, Object> hoarderMap = new HashMap<>();
            hoarderMap.put("steamfriend", steamFriendEntity);
            hoarderMap.put("cnt", hoardcounter);

            topThreeHoardersList.add(hoarderMap);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("topthreefriendships", topthreelongestfriendships);
        map.put("topthreehoarders", topThreeHoardersList);
        map.put("topthreemonthlyhoarders", topThreeMonthlyHoardersList);
        map.put("monthlygain", totalHoardCount);
        map.put("monthlyloss", steamProfileToFriendService.findByRemoveDateGreaterThan(timeService.getLastMonthUnixTime()).size());
        map.put("joinedusers", steamProfileService.findByCreationdateGreaterThanEpoch(timeService.getLastMonthUnixTime()).size());
        return convertObjectToJson(new RestMessageModel("200", "getfrontpage2", map));
    }

    private Long getSteamid(String token, Principal principal) {
        Long steamid = null;

        if (token != null && !token.isEmpty()) {
            steamid = steamUserDetailsService.loadUserByUsername(token).getSteamId();
        } else if (principal != null) {
            steamid = steamUserDetailsService.loadUserByUsername(principal.getName()).getSteamId();
        }

        return steamid;
    }

    private static String convertObjectToJson(Object message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String objectToJson = null;
        try {
            objectToJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return objectToJson;
    }
}
