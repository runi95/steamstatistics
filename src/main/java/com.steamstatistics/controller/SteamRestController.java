package com.steamstatistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.RestMessageModel;
import com.steamstatistics.data.SteamFriendService;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.steamapi.SteamAPICaller;
import com.steamstatistics.steamapi.SteamFriends;
import com.steamstatistics.steamapi.SteamHandler;
import com.steamstatistics.steamapi.TimeService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
        Long steamid = null;

        if (token != null && !token.isEmpty()) {
            steamid = steamUserDetailsService.loadUserByUsername(token).getSteamId();
        } else if (principal != null) {
            steamid = steamUserDetailsService.loadUserByUsername(principal.getName()).getSteamId();
        } else {
            return convertObjectToJson(new RestMessageModel("200", "login"));
        }

        SteamFriends steamFriends = steamHandler.processSteamFriends(steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), steamid), steamid);
        steamHandler.processSteamProfiles(steamid, steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriends.getFriendsList()), steamFriends.getFriendsList());

        return convertObjectToJson(new RestMessageModel("200", "getprofile", steamFriends));
    }

    @RequestMapping("/getfriends")
    public String getFriends(@CookieValue(value = "token", required = false) String token, Principal principal) {
        Long steamid = null;

        if (token != null && !token.isEmpty()) {
            steamid = steamUserDetailsService.loadUserByUsername(token).getSteamId();
        } else if (principal != null) {
            steamid = steamUserDetailsService.loadUserByUsername(principal.getName()).getSteamId();
        }

        SteamFriends steamFriends = steamHandler.processSteamFriends(steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), steamid), steamid);
        steamHandler.processSteamProfiles(steamid, steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriends.getFriendsList()), steamFriends.getFriendsList());

        return convertObjectToJson(new RestMessageModel("200", "getfriends", steamFriends));
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
