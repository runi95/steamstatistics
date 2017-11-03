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
            //LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(steamProfileToFriendEntity.getRemoveDate());
            //String localDateTimeString = (localDateTime.getDayOfMonth() <= 9 ? "0" + localDateTime.getDayOfMonth() : localDateTime.getDayOfMonth()) + "/" + (localDateTime.getMonthValue() <= 9 ? "0" + localDateTime.getMonthValue() : localDateTime.getMonthValue()) + "/" + localDateTime.getYear();
            SteamFriendWithDate steamFriendWithDate = new SteamFriendWithDate(removedFriends.get(steamProfileToFriendEntity.getSteamfriendid()), steamProfileToFriendEntity.getRemoveDate(), timeService);
            removedFriendsWithDate.add(steamFriendWithDate);
        }

        //List<SteamFriendEntity> removedFriends = steamFriendService.getAllInListUpdatedSince(steamidList, 0);

        long lastMonth = timeService.getLastMonthUnixTime();
        List<Object> list = new ArrayList<>();
        list.add(removedFriendsWithDate);
        list.add(steamProfileToFriendService.findByRemoveDateGreaterThan(lastMonth).size());
        list.add(steamProfileToFriendService.findByFriendsinceGreaterThan(lastMonth).size());

        return convertObjectToJson(new RestMessageModel("200", "getremoved", list));
    }

    @RequestMapping("/getfriends")
    public String getFriends(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        Map<Long, SteamFriendEntity> steamFriends = steamHandler.getProfile(steamOpenIdConfig.getClientSecret(), steamid);

        return convertObjectToJson(new RestMessageModel("200", "getfriends", steamFriends));
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
