package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.*;
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

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    @GetMapping(value = "/")
    public String getHomepage() {
        return "home";
    }

    @GetMapping(value = "/profile/accept")
    public String acceptTerms(Principal principal) {
        if (principal != null) {
//            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(principal.getName());
//            SteamProfileEntity steamProfileEntity = new SteamProfileEntity(timeService.getCurrentUnixTime());
//            steamProfileEntity.setSteamid(Long.parseLong(userPrincipal.getSteamId()));
//            steamProfileEntity.setAuthtoken(userPrincipal.getUsername());
//            steamProfileService.save(steamProfileEntity);
        }

        return "test";
    }

    @GetMapping(value = "/home")
    public String getFrontpage() {
        return "frontpage";
    }

    @GetMapping(value = "/test")
    public String getTestPage() {
        return "test";
    }
}
