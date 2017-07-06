package com.steamstatistics.controller;

import com.steamstatistics.backend.OpenIdSession;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import com.steamstatistics.data.SteamProfileService;
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
    OpenIdSession openIdSession;

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    @GetMapping(value="/")
    public String getHomepage(@CookieValue(value = "steamid", required = false) String steamid, Model model, Principal principal) {
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
        }

        System.out.println("steamid: " + steamid);
        System.out.println("principal: " + principal);
        System.out.println("openIdSession: " + openIdSession.getSteamId());

        model.addAttribute("steamprofile", steamProfileModel);
        model.addAttribute("steamprofilestats", steamProfileEntity);
        model.addAttribute("useragreement", userAgreement);

        return "home";
    }
}
