package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "techniques_ranking")
public class TechniquesRanking {
    public int getRankingId() {
        return rankingId;
    }

    public void setRankingId(int rankingId) {
        this.rankingId = rankingId;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getCountUses() {
        return countUses;
    }

    public void setCountUses(int countUses) {
        this.countUses = countUses;
    }

    public int getUsersScore() {
        return usersScore;
    }

    public void setUsersScore(int usersScore) {
        this.usersScore = usersScore;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public PreferencesTAGUser getPreference() {
        return preference;
    }

    public void setPreference(PreferencesTAGUser preference) {
        this.preference = preference;
    }

    public RelaxationTechniqueAudios getAudio() {
        return audio;
    }

    public void setAudio(RelaxationTechniqueAudios audio) {
        this.audio = audio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rankingId;
    private int finalScore;
    private int countUses;
    private int usersScore;
    private LocalDate executionDate;

    @ManyToOne
    private PreferencesTAGUser preference;

    @ManyToOne
    private RelaxationTechniqueAudios audio;

    // Getters and setters
}

