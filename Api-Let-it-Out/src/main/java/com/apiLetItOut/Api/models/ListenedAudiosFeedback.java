package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "ListenedAudiosFeedback")
public class ListenedAudiosFeedback {
    public int getListenedId() {
        return listenedId;
    }

    public void setListenedId(int listenedId) {
        this.listenedId = listenedId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
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

    public RelaxationTechniqueAudios getAudio() {
        return audio;
    }

    public void setAudio(RelaxationTechniqueAudios audio) {
        this.audio = audio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int listenedId;
    private String progress;
    private int score;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private RelaxationTechniqueAudios audio;

    // Getters and setters
}

