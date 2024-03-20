package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "FeedbackEmotions")
public class FeedbackEmotions {
    public int getEmotionsId() {
        return emotionsId;
    }

    public void setEmotionsId(int emotionsId) {
        this.emotionsId = emotionsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emotionsId;
    private String name;

    // Getters and setters
}
