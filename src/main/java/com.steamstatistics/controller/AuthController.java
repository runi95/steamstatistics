package com.steamstatistics.controller;

import com.steamstatistics.backend.SteamOpenId;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.data.SteamProfileService;
import com.steamstatistics.userauth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
public class AuthController {

    @Autowired
    ControllerService controllerService;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    @Autowired
    SteamOpenId steamOpenId;

    @Autowired
    SteamProfileService steamProfileService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request) {
        String url = steamOpenId.login(steamOpenIdConfig.getClientId() + "/openid");

        return "redirect:" + url;
    }

    @GetMapping("/openid")
    public String getCallback(HttpServletRequest request, HttpServletResponse response) {
        String steamidString = steamOpenId.verify(steamOpenIdConfig.getClientId() + "/openid", request.getParameterMap());
        if(steamidString != null && !steamidString.isEmpty()) {
            Long steamid = Long.parseLong(steamidString);

            UserPrincipal userPrincipal = null;
            userPrincipal = steamUserDetailsService.loadBySteamid(steamid);

            if (userPrincipal == null) {
                String userToken = steamUserDetailsService.createUserToken();
                User user = new User();
                user.setSteamid(steamid);
                user.setUserToken(userToken);
                Role role = null;
                boolean isAdmin = controllerService.isAdmin(steamid);
                if(isAdmin) {
                    role = roleRepository.findByName("ROLE_ADMIN");

                } else {
                    role = roleRepository.findByName("ROLE_USER");
                }
                user.setRoles(Arrays.asList(role));

                steamUserDetailsService.saveUser(user);

                userPrincipal = new UserPrincipal(user);
            }

            Cookie cookie = new Cookie("token", userPrincipal.getUserToken());
            cookie.setMaxAge(2600000); // Slightly above 30 days.
            response.addCookie(cookie);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
            auth.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "authsuccess";
        } else {
            throw new IllegalStateException("OpenID could not verify user!");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
