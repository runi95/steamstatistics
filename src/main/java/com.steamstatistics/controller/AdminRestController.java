package com.steamstatistics.controller;

import com.steamstatistics.data.RestMessageModel;
import com.steamstatistics.data.SuggestionEntity;
import com.steamstatistics.data.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

        List<SuggestionEntity> suggestionEntities = suggestionService.getAllSuggestionsWhereApprovedIsNull();

        restMessageModel = new RestMessageModel("200", "suggestion", suggestionEntities);

        return controllerService.convertObjectToJson(restMessageModel);
    }

    @GetMapping("/getapprovedsuggestions")
    public String getApprovedSuggestions() {
        RestMessageModel restMessageModel;

        List<SuggestionEntity> suggestionEntities = suggestionService.getAllSuggestionsWhereApprovedIs(true);

        restMessageModel = new RestMessageModel("200", "suggestion", suggestionEntities);

        return controllerService.convertObjectToJson(restMessageModel);
    }

    @PostMapping("/removesuggestion")
    public String removeSuggestion(long id) {
        RestMessageModel restMessageModel;

        suggestionService.removeSuggestion(id);

        restMessageModel = new RestMessageModel("200", "removed", id);

        return controllerService.convertObjectToJson(restMessageModel);
    }

    @PostMapping("/approvesuggestion")
    public String approveSuggestion(long id) {
        RestMessageModel restMessageModel;

        SuggestionEntity suggestionEntity = suggestionService.getSuggestion(id);
        suggestionEntity.setApproved(true);
        suggestionService.save(suggestionEntity);

        restMessageModel = new RestMessageModel("200", "approved", suggestionEntity);

        return controllerService.convertObjectToJson(restMessageModel);
    }
}
