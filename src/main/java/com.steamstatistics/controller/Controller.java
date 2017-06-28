package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenId;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@org.springframework.stereotype.Controller
public class Controller {
    private final SteamOpenId steamOpenId = new SteamOpenId();

    @GetMapping(value="/")
    public String getHomepage() {
        return "home";
    }

    @GetMapping(value="/auth")
    public String getAuth(HttpServletRequest request) {
        String user = steamOpenId.verify("http://192.168.1.174:8080/auth", request.getParameterMap());

        return "/";
    }

    @GetMapping(value="/trade")
    public String getTrade() {
        return "redirect:" + steamOpenId.login("http://192.168.1.174:8080/auth");
    }
}
