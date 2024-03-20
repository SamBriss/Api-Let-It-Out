package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "TriggerPatterns")
public class TriggerPatterns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int triggerPatternId;

    public int getTriggerPatternId() {
        return triggerPatternId;
    }

    public void setTriggerPatternId(int triggerPatternId) {
        this.triggerPatternId = triggerPatternId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalAttacks() {
        return totalAttacks;
    }

    public void setTotalAttacks(int totalAttacks) {
        this.totalAttacks = totalAttacks;
    }

    public String getGraphicImg() {
        return graphicImg;
    }

    public void setGraphicImg(String graphicImg) {
        this.graphicImg = graphicImg;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    private LocalDate date;
    private int totalAttacks;
    private String graphicImg;

    @ManyToOne
    private UsersTAG userTAG;

    // Getters and setters
}

