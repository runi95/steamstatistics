package com.steamstatistics.controller;

import com.steamstatistics.data.RestMessageModel;
import com.steamstatistics.data.SuggestionEntity;
import com.steamstatistics.data.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    ControllerService controllerService;

    @Autowired
    SuggestionService suggestionService;

    @GetMapping("/getsuggestions")
    public String getSuggestions() {
        RestMessageModel restMessageModel;

        List<SuggestionEntity> suggestionEntities = suggestionService.getAll();

        restMessageModel = new RestMessageModel("200", "suggestion", suggestionEntities);

        return controllerService.convertObjectToJson(restMessageModel);
    }


}