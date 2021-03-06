package com.steamstatistics.data;

import javax.persistence.*;

@Entity
@Table(name = "suggestions")
public class SuggestionEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "steamid", nullable = false)
    private String steamid;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "descript", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private String creationDate;

    @Column(name = "approved", nullable = true)
    private Boolean approved;

    public long getId() {
        return id;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) { this.steamid = steamid; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

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

    public String getCreationDate() { return creationDate; }

    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    public Boolean isApproved() { return approved; }

    public void setApproved(boolean approved) { this.approved = approved; }
}
