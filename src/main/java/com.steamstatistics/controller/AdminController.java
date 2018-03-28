package com.steamstatistics.controller;

import com.steamstatistics.data.SuggestionService;
import com.steamstatistics.userauth.SteamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ControllerService controllerService;

    @Autowired
    SteamUserDetailsService steamUserDetailsService;

    @Autowired
    SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public String getSuggestions(HttpServletRequest request, @CookieValue(value = "token", required = false) String token, Principal principal, Model model) {
        controllerService.checkLogin(request, token, principal, model);

        return "suggestions";
    }

}
