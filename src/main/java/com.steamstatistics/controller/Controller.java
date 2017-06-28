package com.steamstatistics.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping(value="/")
    public String getHomepage() {
        return "home";
    }
}
