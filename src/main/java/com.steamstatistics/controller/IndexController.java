package com.steamstatistics.controller;

import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.services.SteamProfileHandler;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    SteamProfileService steamProfileService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    SteamProfileHandler steamProfileHandler = new SteamProfileHandler();

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    @GetMapping(value="/")
    public String getHomepage(@CookieValue(value = "token", required = false) String token, Model model, Principal principal) {
        String steamid = null;

        if(token != null && !token.isEmpty()) {
            steamid = steamUserDetailsService.loadUserByUsername(token).getSteamId();
        } else if(principal != null) {
            steamid = steamUserDetailsService.loadUserByUsername(principal.getName()).getSteamId();
        }

        SteamProfileModel steamProfileModel = null;
        SteamProfileEntity steamProfileEntity = null;
        if(steamid != null && !steamid.isEmpty()) {

            Long steamidlong = null;
            try{
                steamidlong = Long.parseLong(steamid);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if(steamidlong != null) {
                steamProfileEntity = steamProfileService.get(steamidlong);
            }

            steamProfileModel = steamProfileHandler.getProfile(steamid);
        }

        model.addAttribute("steamprofile", steamProfileModel);
        model.addAttribute("steamprofilestats", steamProfileEntity);
        model.addAttribute("useragreement", userAgreement);

        return "home";
    }

    @GetMapping(value="/profile/accept")
    public String acceptTerms(Principal principal) {
        if(principal != null) {
            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(principal.getName());
            SteamProfileEntity steamProfileEntity = new SteamProfileEntity();
            steamProfileEntity.setSteamid(Long.parseLong(userPrincipal.getSteamId()));
            steamProfileEntity.setAuthtoken(userPrincipal.getUsername());
            steamProfileService.save(steamProfileEntity);
        }

        return "test";
    }

    @GetMapping(value="/test")
    public String getTestPage() {
        return "test";
    }
}
