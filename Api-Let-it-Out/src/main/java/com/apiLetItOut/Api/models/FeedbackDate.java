package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "FeedbackDate")
public class FeedbackDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackDateId;

    @ManyToOne
    private ListenedAudiosFeedback listenedAudio;

    private LocalDate feedbackDate;

    @ManyToOne
    private AttackRegisters attackRegister;

    public int getFeedbackDateId() {
        return feedbackDateId;
    }

    // Setter for feedbackDateId
    public void setFeedbackDateId(int feedbackDateId) {
        this.feedbackDateId = feedbackDateId;
    }

    // Getter for listenedAudio
    public ListenedAudiosFeedback getListenedAudio() {
        return listenedAudio;
    }

    // Setter for listenedAudio
    public void setListenedAudio(ListenedAudiosFeedback listenedAudio) {
        this.listenedAudio = listenedAudio;
    }

    // Getter for feedbackDate
    public LocalDate getFeedbackDate() {
        return feedbackDate;
    }

    // Setter for feedbackDate
    public void setFeedbackDate(LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    // Getter for attackRegister
    public AttackRegisters getAttackRegister() {
        return attackRegister;
    }

    // Setter for attackRegister
    public void setAttackRegister(AttackRegisters attackRegister) {
        this.attackRegister = attackRegister;
    }
}
