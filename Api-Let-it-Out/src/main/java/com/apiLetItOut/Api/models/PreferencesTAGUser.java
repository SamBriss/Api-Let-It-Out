package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "PreferencesTAGUser")
public class PreferencesTAGUser {
    public int getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public PreferencesCategories getCategory() {
        return category;
    }

    public void setCategory(PreferencesCategories category) {
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preferenceId;
    private String name;
    private int score;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private PreferencesCategories category;

    // Getters and setters
}
