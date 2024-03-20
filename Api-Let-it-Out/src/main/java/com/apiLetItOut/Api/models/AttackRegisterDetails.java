package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "AttackRegisterDetails")
public class AttackRegisterDetails {
    public int getAttackId() {
        return attackId;
    }

    public void setAttackId(int attackId) {
        this.attackId = attackId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public String getExplanationResume() {
        return explanationResume;
    }

    public void setExplanationResume(String explanationResume) {
        this.explanationResume = explanationResume;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getEmotions() {
        return emotions;
    }

    public void setEmotions(String emotions) {
        this.emotions = emotions;
    }

    public String getPhysicalSensations() {
        return physicalSensations;
    }

    public void setPhysicalSensations(String physicalSensations) {
        this.physicalSensations = physicalSensations;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public char getTypeOfThought() {
        return typeOfThought;
    }

    public void setTypeOfThought(char typeOfThought) {
        this.typeOfThought = typeOfThought;
    }

    public AttackRegisters getAttackRegister() {
        return attackRegister;
    }

    public void setAttackRegister(AttackRegisters attackRegister) {
        this.attackRegister = attackRegister;
    }

    public String getReportURL() {
        return reportURL;
    }

    public void setReportURL(String reportURL) {
        this.reportURL = reportURL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attackId;
    private String place;
    private String motive;
    private String explanationResume;
    private int intensity;
    private String emotions;
    private String physicalSensations;
    private String thoughts;
    private char typeOfThought;
    private String reportURL;

    @ManyToOne
    private AttackRegisters attackRegister;

    // Getters and setters
}

