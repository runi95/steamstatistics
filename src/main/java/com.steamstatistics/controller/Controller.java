package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileModel;
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

    @GetMapping(value="/")
    public String getHomepage(@CookieValue(value = "steamid", required = false) String steamid, Model model) {
        System.out.println("steasmid: " + steamid);

        SteamProfileModel steamProfileModel = null;
        if(steamid != null && !steamid.isEmpty())
            steamProfileModel = steamProfileHandler.getProfile(steamid);

        model.addAttribute("steamprofile", steamProfileModel);

        return "home";
    }

    @GetMapping(value="/auth")
    public String getAuth(HttpServletRequest request, HttpServletResponse response) {
        String user = steamOpenId.verify("http://192.168.1.174:8080/auth", request.getParameterMap());

        System.out.println("setting cookie to: " + user);
        response.addCookie(new Cookie("steamid", user));

        return "/";
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
