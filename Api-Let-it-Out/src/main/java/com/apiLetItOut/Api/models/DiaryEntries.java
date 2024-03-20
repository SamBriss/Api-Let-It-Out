package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "DiaryEntries")
public class DiaryEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diaryId;

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public FeedbackEmotions getEmotion() {
        return emotion;
    }

    public void setEmotion(FeedbackEmotions emotion) {
        this.emotion = emotion;
    }

    private LocalDate date;
    private LocalTime hour;
    private String text;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private FeedbackEmotions emotion;

    // Getters and setters
}

