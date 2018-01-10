package com.steamstatistics.controller;

import com.steamstatistics.data.SuggestionEntity;
import com.steamstatistics.data.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    SuggestionService suggestionService;

    @GetMapping("/suggestions")
    public String getSuggestions(Model model) {
        List<SuggestionEntity> suggestions = suggestionService.getAll();
        model.addAttribute("suggestions", suggestions);

        return "adminsuggestions";
    }

}
