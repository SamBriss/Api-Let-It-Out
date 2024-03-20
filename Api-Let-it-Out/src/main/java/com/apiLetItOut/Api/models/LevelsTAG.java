package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name="LevelsTAG")
public class LevelsTAG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelTAGId;

    public int getLevelTAGId() {
        return levelTAGId;
    }

    public void setLevelTAGId(int levelTAGId) {
        this.levelTAGId = levelTAGId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    private String level;
}
