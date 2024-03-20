package com.apiLetItOut.Api.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ManualAttackRegister")
public class ManualAttackRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manualAttackRegisterId;
    private LocalDate date;
    private LocalTime hour;
    private String place;
    private String motive;
    private String explanationResume;
    private int intensity;
    private String emotions;
    private String physicalSensations;
    private String thoughts;
    private String typeOfThought;

    @ManyToOne
    private AttacksMethods attackMethods;

    @ManyToOne
    private UsersTAG usersTAG;

    public int getManualAttackRegisterId() {
        return manualAttackRegisterId;
    }
    public void setManualAttackRegisterId(int manualAttackRegisterId) {
        this.manualAttackRegisterId = manualAttackRegisterId;
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
    public String getTypeOfThought() {
        return typeOfThought;
    }
    public void setTypeOfThought(String typeOfThought) {
        this.typeOfThought = typeOfThought;
    }    
}
