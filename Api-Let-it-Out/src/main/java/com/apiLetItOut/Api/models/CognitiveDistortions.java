package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "CognitiveDistortions")
public class CognitiveDistortions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cognitiveDistortionId;
    private String dateSituation;
    private String thought;
    private String physicalSensation;
    private String emotionalFeeling;
    private String consequence;
    private String cognitiveDistortion;
    
    @ManyToOne
    private UsersTAG usersTAG;

    public int getCognitiveDistortionId() {
        return cognitiveDistortionId;
    }

    public void setCognitiveDistortionId(int cognitiveDistortionId) {
        this.cognitiveDistortionId = cognitiveDistortionId;
    }

    public String getDateSituation() {
        return dateSituation;
    }

    public void setDateSituation(String dateSituation) {
        this.dateSituation = dateSituation;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getPhysicalSensation() {
        return physicalSensation;
    }

    public void setPhysicalSensation(String physicalSensation) {
        this.physicalSensation = physicalSensation;
    }

    public String getEmotionalFeeling() {
        return emotionalFeeling;
    }

    public void setEmotionalFeeling(String emotionalFeeling) {
        this.emotionalFeeling = emotionalFeeling;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getCognitiveDistortion() {
        return cognitiveDistortion;
    }

    public void setCognitiveDistortion(String cognitiveDistortion) {
        this.cognitiveDistortion = cognitiveDistortion;
    }

    public UsersTAG getUsersTAG() {
        return usersTAG;
    }

    public void setUsersTAG(UsersTAG usersTAG) {
        this.usersTAG = usersTAG;
    }
    
}
