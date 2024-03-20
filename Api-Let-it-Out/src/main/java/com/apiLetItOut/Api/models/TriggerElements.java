package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "TriggerElements")
public class TriggerElements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int triggerElementId;

    public int getTriggerElementId() {
        return triggerElementId;
    }

    public void setTriggerElementId(int triggerElementId) {
        this.triggerElementId = triggerElementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getrPearson() {
        return rPearson;
    }

    public void setrPearson(int rPearson) {
        this.rPearson = rPearson;
    }

    public int getIndividualProbability() {
        return individualProbability;
    }

    public void setIndividualProbability(int individualProbability) {
        this.individualProbability = individualProbability;
    }

    public TriggerPatterns getTriggerPattern() {
        return triggerPattern;
    }

    public void setTriggerPattern(TriggerPatterns triggerPattern) {
        this.triggerPattern = triggerPattern;
    }

    public DiccionaryCategories getCategoryDictionary() {
        return categoryDictionary;
    }

    public void setCategoryDictionary(DiccionaryCategories categoryDictionary) {
        this.categoryDictionary = categoryDictionary;
    }

    private String name;
    private int count;
    private int rPearson;
    private int individualProbability;

    @ManyToOne
    private TriggerPatterns triggerPattern;

    @ManyToOne
    private DiccionaryCategories categoryDictionary;

    // Getters and setters
}

