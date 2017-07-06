package com.steamstatistics.controller;

import com.steamstatistics.backend.OpenIdSession;
import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.backend.SteamOpenIdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private OpenIdSession openIdSession;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Autowired
    SteamOpenId steamOpenId;

    @GetMapping("/login")
    public String getLogin() {
        return "redirect:" + steamOpenId.login(steamOpenIdConfig.getClientId() + "/auth/callback");
    }

    @GetMapping("/callback")
    public String getCallback(@CookieValue(value = "steamid", required = false) String steamid, HttpServletRequest request, HttpServletResponse response) {
        if(steamid == null || steamid.isEmpty()) {
            String user = steamOpenId.verify(steamOpenIdConfig.getClientId() + "/auth/callback", request.getParameterMap());

            openIdSession.setSteamId(user);

            response.addCookie(new Cookie("steamid", user));
        }

        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session)  {
        session.invalidate();
        return "redirect:/";
    }
}
