package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.services.SteamProfileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller
public class Controller {
    @Value("${auth.baseurl}")
    private String baseurl;

    private final String userAgreement = "This website stores information about your steam profile and friends list to keep up to date, by clicking accept you agree to let us gather and store information from your steam profile. Users can delete the stored data at any given time by logging in through the Steam again and clicking the delete button. The information gathered will be public information from your steam profile which means this site can't function if your profile is on private. Click accept to continue";

    private final SteamOpenId steamOpenId = new SteamOpenId();

    @Autowired
    private SteamProfileHandler steamProfileHandler;

    @Autowired
    private SteamProfileService steamProfileService;

    @GetMapping(value="/")
    public String getHomepage(@CookieValue(value = "steamid", required = false) String steamid, Model model) {
        SteamProfileModel steamProfileModel = null;
        SteamProfileEntity steamProfileEntity = null;
        if(steamid != null && !steamid.isEmpty()) {
            steamProfileModel = steamProfileHandler.getProfile(steamid);

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

        model.addAttribute("steamprofile", steamProfileModel);
        model.addAttribute("steamprofilestats", steamProfileEntity);
        model.addAttribute("useragreement", userAgreement);

        return "home";
    }

    @PostMapping(value = "/")
    public String userAgreement() {

        return "redirect:/";
    }

    @GetMapping(value="/auth")
    public String getAuth(@CookieValue(value = "steamid", required = false) String steamid, HttpServletRequest request, HttpServletResponse response) {
        if(steamid == null || steamid.isEmpty()) {
            String user = steamOpenId.verify(baseurl + "/auth", request.getParameterMap());

            response.addCookie(new Cookie("steamid", user));
        }

        return "redirect:/";
    }

    @GetMapping(value="/trade")
    public String getTrade() {
        return "redirect:" + steamOpenId.login(baseurl + "/auth");
    }

    @GetMapping(value="/login")
    public String getLogin() {
        return "login";
    }
}
