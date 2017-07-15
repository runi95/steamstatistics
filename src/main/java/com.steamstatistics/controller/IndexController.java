package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.steamapi.SteamAPICaller;
import com.steamstatistics.steamapi.SteamFriends;
import com.steamstatistics.steamapi.SteamHandler;
import com.steamstatistics.steamapi.TimeService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashMap;

@Controller
public class IndexController {

    @Autowired
    SteamProfileService steamProfileService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    SteamAPICaller steamAPICaller = new SteamAPICaller();

    SteamHandler steamHandler = new SteamHandler();

    TimeService timeService = new TimeService();

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    @GetMapping(value = "/")
    public String getHomepage(@CookieValue(value = "token", required = false) String token, Model model, Principal principal) {
        String steamid = null;

        if (token != null && !token.isEmpty()) {
            steamid = steamUserDetailsService.loadUserByUsername(token).getSteamId();
        } else if (principal != null) {
            steamid = steamUserDetailsService.loadUserByUsername(principal.getName()).getSteamId();
        }

        SteamProfileEntity steamProfileEntity = null;
        SteamProfileModel steamProfileModel = null;
        SteamFriends steamFriends = null;
        HashMap<String, SteamProfileModel> steamProfiles = null;
        if (steamid != null && !steamid.isEmpty()) {

            Long steamidlong = null;
            try {
                steamidlong = Long.parseLong(steamid);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (steamidlong != null && steamProfileEntity == null) {
                steamProfileEntity = steamProfileService.get(steamidlong);
            }

            steamFriends = steamHandler.processSteamFriends(steamAPICaller.getFriendList(steamOpenIdConfig.getClientSecret(), steamid), steamid);
            steamProfiles = steamHandler.processSteamProfiles(steamAPICaller.getPlayerSummaries(steamOpenIdConfig.getClientSecret(), steamFriends.getFriendsList()));
            steamProfileModel = steamProfiles.get(steamid);
            if (steamProfileModel != null) {
                System.out.println(steamProfileModel.getPrivacyState());
                System.out.println(steamProfileModel.getOnlineState());
            }
        }

        model.addAttribute("steamprofile", steamProfileModel);
        model.addAttribute("steamfriends", steamFriends);
        model.addAttribute("steamprofilestats", steamProfileEntity);
        model.addAttribute("useragreement", userAgreement);

        return "home";
    }

    @GetMapping(value = "/profile/accept")
    public String acceptTerms(Principal principal) {
        if (principal != null) {
            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(principal.getName());
            SteamProfileEntity steamProfileEntity = new SteamProfileEntity(timeService.getCurrentUnixTime());
            steamProfileEntity.setSteamid(Long.parseLong(userPrincipal.getSteamId()));
            steamProfileEntity.setAuthtoken(userPrincipal.getUsername());
            steamProfileService.save(steamProfileEntity);
        }

        return "test";
    }

    @GetMapping(value = "/test")
    public String getTestPage() {
        return "test";
    }
}
