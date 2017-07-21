package com.steamstatistics.controller;

import com.steamstatistics.backend.OpenIdSession;
import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamProfileEntity;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.User;
import com.steamstatistics.userauth.UserPrincipal;
import org.openid4java.message.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Autowired
    SteamOpenId steamOpenId;

    @Autowired
    SteamProfileService steamProfileService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request) {
        AuthRequest authReq = steamOpenId.login(steamOpenIdConfig.getClientId() + "/login/openid");
        String url = authReq.getDestinationUrl(true);

        return "redirect:" + url;
    }

    @GetMapping("/login/openid")
    public String getCallback(HttpServletRequest request, HttpServletResponse response) {
        String steamidString = steamOpenId.verify(steamOpenIdConfig.getClientId() + "/login/openid", request.getParameterMap());
        Long steamid = Long.parseLong(steamidString);

        UserPrincipal userPrincipal = null;
        userPrincipal = steamUserDetailsService.findUserBySteamId(steamid);

        if(userPrincipal == null) {
            String userToken = steamUserDetailsService.createUserToken();
            User user = new User();
            user.setSteamId(steamid);
            user.setUserToken(userToken);
            steamUserDetailsService.saveUser(user);

            userPrincipal = new UserPrincipal(user);
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        auth.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "test";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/test";
    }
}
