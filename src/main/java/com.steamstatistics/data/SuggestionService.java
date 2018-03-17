package com.steamstatistics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {

    @Autowired
    SuggestionRepository suggestionRepository;

    public void save(SuggestionEntity suggestionEntity) {
        suggestionRepository.save(suggestionEntity);
    }

    public List<SuggestionEntity> getAll() {
        return suggestionRepository.findAll();
    }

    public List<SuggestionEntity> getAllSuggestionsWhereApprovedIs(boolean approved) { return suggestionRepository.findAllByApproved(approved); }

    public List<SuggestionEntity> getAllSuggestionsWhereApprovedIsNull() { return suggestionRepository.findAllByApprovedIsNull(); }

    public SuggestionEntity getSuggestion(long id) {
        return suggestionRepository.findById(id);
    }

    public void removeSuggestion(long id) { suggestionRepository.deleteById(id); }

}
