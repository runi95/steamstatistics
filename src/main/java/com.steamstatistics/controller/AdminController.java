package com.steamstatistics.controller;

import com.steamstatistics.data.SuggestionEntity;
import com.steamstatistics.data.SuggestionService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public String getSuggestions(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model) {
        checkLogin(request, token, principal, model);

        return "suggestions";
    }

    public void checkLogin(HttpServletRequest request, String token, Principal principal, Model model) {
        if(principal != null) {
            model.addAttribute("steamid", principal.getName());
        } else if(token != null && !token.isEmpty()) {
            UserPrincipal userPrincipal = steamUserDetailsService.loadUserByUsername(token);

            if(userPrincipal != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

                model.addAttribute("steamid", userPrincipal.getSteamId());
            }
        }
    }

}
