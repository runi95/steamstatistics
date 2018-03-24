package com.steamstatistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.*;
import com.steamstatistics.steamapi.*;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

@RestController
public class SteamRestController {

    @Autowired
    ControllerService controllerService;

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

    @Autowired
    SuggestionService suggestionService;

    @RequestMapping("/getprofile")
    public String getProfile(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = controllerService.getSteamid(token, principal);

        if(steamid == null) {
            return controllerService.convertObjectToJson(new RestMessageModel("200", "login"));
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

        return controllerService.convertObjectToJson(new RestMessageModel("200", "getprofile", list));
    }

    private String getFullProfileOfSteamid(long steamid) {
        Map<String, Object> map = new HashMap<>();

        SteamProfileEntity steamProfile = steamProfileService.get(steamid);
        if(steamProfile == null) {
            return controllerService.convertObjectToJson(new RestMessageModel("204", "getfullprofile", "nocontent"));
        }

        SteamFriendEntity steamFriendProfile = steamFriendService.get(steamid);

        LocalDateTime localTimeDate = timeService.getLocalDateTimeFromUnix(steamProfile.getCreationdate());
        String jdate = (localTimeDate.getDayOfMonth() <= 9 ? "0" + localTimeDate.getDayOfMonth() : localTimeDate.getDayOfMonth()) + "/" + (localTimeDate.getMonthValue() <= 9 ? "0" + localTimeDate.getMonthValue() : localTimeDate.getMonthValue()) + "/" + localTimeDate.getYear();

        map.put("profile", steamFriendProfile);
        map.put("jdate", jdate);

        List<SteamProfileToFriendEntity> addedFriendsList = steamProfileToFriendService.findAllAddedFriendsDescWhereRemoveDateIsNull(steamid);
        List<Long> addedSteamidList = new ArrayList<>();
        addedFriendsList.forEach((s) -> addedSteamidList.add(s.getSteamfriendid()));
        List<SteamFriendEntity> addedFriends = steamFriendService.getAllInList(addedSteamidList);
        Map<Long, SteamFriendEntity> addedFriendMap = new HashMap<>();
        addedFriends.forEach((s) -> addedFriendMap.put(s.getSteamid(), s));

        List<Map<String, Object>> addedFriendsWithDate = new ArrayList<>();
        for(SteamProfileToFriendEntity steamFriend : addedFriendsList) {
            Map<String, Object> friendWithDate = new HashMap<>();
            friendWithDate.put("steamFriendEntity", addedFriendMap.get(steamFriend.getSteamfriendid()));
            friendWithDate.put("localDateTimeString", prettifyDate(steamFriend.getFriendsince()));
            addedFriendsWithDate.add(friendWithDate);
        }

        long lastMonth = timeService.getLastMonthUnixTime();

        List<Object> addedList = new ArrayList<>();
        addedList.add(addedFriendsWithDate);
        addedList.add(steamProfileToFriendService.findByFriendsinceGreaterThanAndProfileid(lastMonth, steamid).size());

        map.put("added", addedList);

        List<SteamProfileToFriendEntity> removedFriendsList = steamProfileToFriendService.getRemovedFriends(steamid);
        List<Long> removedSteamidList = new ArrayList<>();
        removedFriendsList.forEach((s) -> removedSteamidList.add(s.getSteamfriendid()));
        List<SteamFriendEntity> removedFriends = steamFriendService.getAllInList(removedSteamidList);
        Map<Long, SteamFriendEntity> removedFriendMap = new HashMap<>();
        removedFriends.forEach((s) -> removedFriendMap.put(s.getSteamid(), s));


        List<Map<String, Object>> removedFriendsWithDate = new ArrayList<>();
        for(SteamProfileToFriendEntity steamProfileToFriendEntity : removedFriendsList) {
            Map<String, Object> friendWithDate = new HashMap<>();
            friendWithDate.put("steamFriendEntity", removedFriendMap.get(steamProfileToFriendEntity.getSteamfriendid()));
            friendWithDate.put("localDateTimeString", prettifyDate(steamProfileToFriendEntity.getFriendsince()) + " - " + prettifyDate(steamProfileToFriendEntity.getRemoveDate()));
            removedFriendsWithDate.add(friendWithDate);
        }

        List<Object> removedList = new ArrayList<>();
        removedList.add(removedFriendsWithDate);
        removedList.add(steamProfileToFriendService.findByRemoveDateGreaterThanAndProfileid(lastMonth, steamid).size());

        map.put("removed", removedList);

        return controllerService.convertObjectToJson(new RestMessageModel("200", "getfullprofile", map));
    }

    @RequestMapping("/getfullprofile/{steamid}")
    public String getFullProfileOfTarget(@CookieValue(value = "token", required = false) String token, Principal principal, @PathVariable String steamid) {
        Long principalSteamid = controllerService.getSteamid(token, principal);

        if(principalSteamid == null) {
            return controllerService.convertObjectToJson(new RestMessageModel("200", "login"));
        }

        Long targetSteamid = null;
        try {
            targetSteamid = Long.parseLong(steamid);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if(targetSteamid == null) {
            targetSteamid = principalSteamid;
        }

        return getFullProfileOfSteamid(targetSteamid);
    }

    @RequestMapping("/getfullprofile")
    public String getFullProfile(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = controllerService.getSteamid(token, principal);

        if(steamid == null) {
            return controllerService.convertObjectToJson(new RestMessageModel("200", "login"));
        }

        return getFullProfileOfSteamid(steamid);
    }

    @RequestMapping("/monthlygraph")
    public String getMonthlyGraph(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = controllerService.getSteamid(token, principal);

        long[] times = timeService.getEveryMonthOfAYear();
        Map<String, Object> map = new HashMap<>();

        List<List<Object>> gainedFriends = new ArrayList<>();
        List<List<Object>> lostFriends = new ArrayList<>();
        List<List<Object>> personalGain = new ArrayList<>();
        List<List<Object>> personalLoss = new ArrayList<>();

        Long usersCount = Math.max(1, steamProfileService.getProfileCount());
        for(int i = times.length - 1; i > 0; i--) {
            LocalDateTime dateTime = timeService.getLocalDateTimeFromUnix(times[i]);
            String displayName = dateTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            Long monthlyGain = steamProfileToFriendService.countFriendSinceBetweenFromAndTo(times[i], times[i - 1]);
            List<Object> gainArray = new ArrayList<>();
            gainArray.add(displayName);
            gainArray.add(monthlyGain / usersCount);
            gainedFriends.add(gainArray);

            Long monthlyLoss = steamProfileToFriendService.countFriendsRemovedBetweenFromAndTo(times[i], times[i - 1]);
            List<Object> lossArray = new ArrayList<>();
            lossArray.add(displayName);
            lossArray.add(monthlyLoss / usersCount);
            lostFriends.add(lossArray);

            if(i == 1) {
                map.put("monthlygain", monthlyGain);
                map.put("monthlyloss", monthlyLoss);

                int newAccounts = steamProfileService.findByCreationdateGreaterThanEpoch(times[i]).size();
                map.put("joinedusers", newAccounts);
            }

            if(steamid != null) {
                Long personalGainCount = steamProfileToFriendService.countFriendsSinceBetweenFromAndToForUser(times[i], times[i - 1], steamid);
                Long personalLossCount = steamProfileToFriendService.countFriendsRemovedBetweenFromAndToForUser(times[i], times[i - 1], steamid);

                List<Object> personalGainArray = new ArrayList<>();
                List<Object> personalLossArray = new ArrayList<>();

                personalGainArray.add(displayName);
                personalGainArray.add(personalGainCount);
                personalLossArray.add(displayName);
                personalLossArray.add(personalLossCount);
                personalGain.add(personalGainArray);
                personalLoss.add(personalLossArray);
            }
        }

        map.put("averagegained", gainedFriends);
        map.put("averagelost", lostFriends);
        if(steamid != null) {
            map.put("personalgained", personalGain);
            map.put("personallost", personalLoss);
        }

        return controllerService.convertObjectToJson(new RestMessageModel("200", "monthlygraph", map));
    }

    @RequestMapping("/getfrontpage")
    public String getFrontpage() {
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
            javascriptObject.put("profilelink", "/profile/" + steamFriendEntity.getSteamid());

            topthreelongestfriendships.add(javascriptObject);
        }

        long startOfCurrentMonth = timeService.getStartOfCurrentMonthInUnixTime();

        List<Map<String, Object>> topThreeMonthlyHoardersList = new ArrayList<>();

        Object[][] lastMonthGainedFriendsList = steamProfileToFriendService.findByFriendsinceGreaterThanTwo(startOfCurrentMonth);
        for(int i = 0; i < 3 && i < lastMonthGainedFriendsList.length; i++) {
            long steamprofileid = (long)lastMonthGainedFriendsList[i][0];
            long hoardcounter = (long)lastMonthGainedFriendsList[i][1];

            SteamFriendEntity steamFriendEntity = steamFriendService.get(steamprofileid);

            Map<String, Object> hoarderMap = new HashMap<>();
            hoarderMap.put("steamfriend", steamFriendEntity);
            hoarderMap.put("cnt", hoardcounter);
            hoarderMap.put("profilelink", "/profile/" + steamFriendEntity.getSteamid());

            topThreeMonthlyHoardersList.add(hoarderMap);
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
            hoarderMap.put("profilelink", "/profile/" + steamFriendEntity.getSteamid());

            topThreeHoardersList.add(hoarderMap);
        }

        List<SuggestionEntity> approvedSuggestionEntities = suggestionService.getAllSuggestionsWhereApprovedIs(true);

        Map<String, Object> map = new HashMap<>();
        map.put("topthreefriendships", topthreelongestfriendships);
        map.put("topthreehoarders", topThreeHoardersList);
        map.put("topthreemonthlyhoarders", topThreeMonthlyHoardersList);
        map.put("approvedsuggestions", approvedSuggestionEntities);
        return controllerService.convertObjectToJson(new RestMessageModel("200", "getfrontpage", map));
    }

    @PostMapping("/suggestion")
    public String suggestions(Principal principal, @ModelAttribute("suggestionForm") SuggestionForm suggestionForm, BindingResult bindingResult) {
        RestMessageModel restMessageModel = null;

        if(principal != null) {
            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(principal.getName());

            if (suggestionForm.getCategory() != null && suggestionForm.getDescription() != null && !suggestionForm.getCategory().isEmpty() && !suggestionForm.getDescription().isEmpty()) {
                SuggestionEntity suggestionEntity = new SuggestionEntity();
                suggestionEntity.setSteamid(Long.toString(userPrincipal.getSteamId()));
                suggestionEntity.setTitle(suggestionForm.getTitle());
                suggestionEntity.setCategory(suggestionForm.getCategory());
                suggestionEntity.setDescription(suggestionForm.getDescription());
                suggestionEntity.setCreationDate(timeService.prettifyDate(timeService.getLocalDateTimeFromUnix(timeService.getCurrentUnixTime())));
                suggestionService.save(suggestionEntity);

                Map<String, Object> map = new HashMap<>();
                restMessageModel = new RestMessageModel("200", "suggestion", null);
            } else {
                restMessageModel = new RestMessageModel("408", "suggestion", null);
            }
        }

        return controllerService.convertObjectToJson(restMessageModel);
    }

    private String prettifyDate(long epochSeconds) {
        if(epochSeconds == 0)
            return "";

        LocalDateTime localDateTime = timeService.getLocalDateTimeFromUnix(epochSeconds);

        String month = Integer.toString(localDateTime.getMonthValue()), day = Integer.toString(localDateTime.getDayOfMonth());
        if(month.length() == 1)
            month = "0" + month;
        if(day.length() == 1)
            day = "0" + day;

        return day + "." + month + "." + localDateTime.getYear();
    }
}
