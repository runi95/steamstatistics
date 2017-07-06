package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.services.SteamProfileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller
public class Controller {
    private final SteamOpenId steamOpenId = new SteamOpenId();

    @Autowired
    private SteamProfileHandler steamProfileHandler;

    @Autowired
    private SteamProfileService steamProfileService;

    @GetMapping(value="/")
    public String getHomepage(@CookieValue(value = "steamid", required = false) String steamid, Model model) {
        SteamProfileModel steamProfileModel = null;
        if(steamid != null && !steamid.isEmpty())
            steamProfileModel = steamProfileHandler.getProfile(steamid);

        model.addAttribute("steamprofile", steamProfileModel);

        return "home";
    }

    @GetMapping(value="/auth")
    public String getAuth(@CookieValue(value = "steamid", required = false) String steamid, HttpServletRequest request, HttpServletResponse response) {
        if(steamid == null || steamid.isEmpty()) {
            String user = steamOpenId.verify("http://192.168.1.174:8080/auth", request.getParameterMap());

            response.addCookie(new Cookie("steamid", user));
        }

        return "redirect:/";
    }

    @GetMapping(value="/trade")
    public String getTrade() {
        return "redirect:" + steamOpenId.login("http://192.168.1.174:8080/auth");
    }

    @GetMapping(value="/login")
    public String getLogin() {
        return "login";
    }
}
