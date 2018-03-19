package com.steamstatistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.steamstatistics.backend.SteamOpenIdConfig;
import com.steamstatistics.userauth.SteamUserDetailsService;
import com.steamstatistics.userauth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class ControllerService {

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SteamOpenIdConfig steamOpenIdConfig;

    public boolean isAdmin(long steamid) {
        for(int i = 0; i < steamOpenIdConfig.getAdminList().length; i++) {
            if(steamOpenIdConfig.getAdminList()[i].equals(Long.toString(steamid))) {
                return true;
            }
        }

        return false;
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

    public Long getSteamid(String token, Principal principal) {
        Long steamid = null;

        UserPrincipal usr = null;

        if (token != null && !token.isEmpty()) {
            usr = steamUserDetailsService.loadByToken(token);
        } else if (principal != null) {
            usr = steamUserDetailsService.loadUserByUsername(principal.getName());
        }

        if(usr != null)
            steamid = usr.getSteamId();

        return steamid;
    }

    public String convertObjectToJson(Object message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String objectToJson = null;
        try {
            objectToJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return objectToJson;
    }
}
