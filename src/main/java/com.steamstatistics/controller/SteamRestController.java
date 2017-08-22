package com.steamstatistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.steamstatistics.backend.Frontpage;
import com.steamstatistics.backend.LongestFriendship;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.RestMessageModel;
import com.steamstatistics.data.SteamFriendEntity;
import com.steamstatistics.data.SteamFriendService;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.steamapi.*;
import com.steamstatistics.userauth.SteamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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

        Map<Long, SteamFriendEntity> steamFriends = steamHandler.getProfile(steamOpenIdConfig.getClientSecret(), steamid);

        steamFriendService.updateFriendsList(steamFriends, steamid);

        return convertObjectToJson(new RestMessageModel("200", "getprofile", steamFriends.get(steamid)));
    }

    @RequestMapping("/getremoved")
    public String getRemoved(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        if(steamid == null) {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }

        return convertObjectToJson(new RestMessageModel("200", "getremoved", steamFriendService.getRemovedSteamFriends(steamid)));
    }

    @RequestMapping("/getfriends")
    public String getFriends(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = getSteamid(token, principal);

        Map<Long, SteamFriendEntity> steamFriends = steamHandler.getProfile(steamOpenIdConfig.getClientSecret(), steamid);

        return convertObjectToJson(new RestMessageModel("200", "getfriends", steamFriends));
    }

    @RequestMapping("/getfrontpage")
    public String getFrontpage(@CookieValue(value = "token", required = false) String token, Principal principal) {
        LongestFriendship longestFriendship = steamFriendService.getLongestFriendship();
        List<SteamFriendEntity> ruinedFriendshipsList = steamFriendService.findByRemoveDateGreaterThan(timeService.getLastMonthUnixTime());
        List<SteamFriendEntity> bondedFriendshipsList = steamFriendService.findByFriendsinceGreaterThan(timeService.getLastMonthUnixTime());
        int ruinedFriendships = ruinedFriendshipsList.size(), bondedFriendships = bondedFriendshipsList.size();

        Frontpage frontpage = new Frontpage(ruinedFriendships, bondedFriendships, longestFriendship);

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
