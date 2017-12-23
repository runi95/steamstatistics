package com.steamstatistics.controller;

import org.hibernate.validator.constraints.NotEmpty;

public class SuggestionForm {
    @NotEmpty
    private String category;

    @NotEmpty
    private String description;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
