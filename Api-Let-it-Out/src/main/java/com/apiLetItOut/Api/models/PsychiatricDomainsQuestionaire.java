package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "PsychiatricDomainsQuestionaire")
public class PsychiatricDomainsQuestionaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int psychiatricDomainQuestionaireId;

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public PsychiatricDomains getDomain() {
        return domain;
    }

    public void setDomain(PsychiatricDomains domain) {
        this.domain = domain;
    }

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private LocalDate executionDate;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private PsychiatricDomains domain;

    public int getPsychiatricDomainQuestionaireId() {
        return psychiatricDomainQuestionaireId;
    }

    public void setPsychiatricDomainQuestionaireId(int psychiatricDomainQuestionaireId) {
        this.psychiatricDomainQuestionaireId = psychiatricDomainQuestionaireId;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }
}
