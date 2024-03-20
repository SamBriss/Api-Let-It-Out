package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "ActivitiesFromTherapist")
public class ActivitiesFromTherapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityTId;

    public int getActivityTId() {
        return activityTId;
    }

    public void setActivityTId(int activityTId) {
        this.activityTId = activityTId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateAsign() {
        return dateAsign;
    }

    public void setDateAsign(LocalDate dateAsign) {
        this.dateAsign = dateAsign;
    }

    public LocalDate getDateMax() {
        return dateMax;
    }

    public void setDateMax(LocalDate dateMax) {
        this.dateMax = dateMax;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public UsersTherapists getUserTherapist() {
        return userTherapist;
    }

    public void setUserTherapist(UsersTherapists userTherapist) {
        this.userTherapist = userTherapist;
    }

    private String label;
    private String description;
    private LocalDate dateAsign;
    private LocalDate dateMax;
    private boolean completed;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private UsersTherapists userTherapist;

    // Getters and setters
}

