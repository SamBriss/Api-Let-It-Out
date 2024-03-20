package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "RelaxationTechniqueAudios")
public class RelaxationTechniqueAudios {
    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
        this.audioId = audioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSchedule() {
        return schedule;
    }

    public void setSchedule(char schedule) {
        this.schedule = schedule;
    }

    public char getAuditory() {
        return auditory;
    }

    public void setAuditory(char auditory) {
        this.auditory = auditory;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getTechniqueLevel() {
        return techniqueLevel;
    }

    public void setTechniqueLevel(int techniqueLevel) {
        this.techniqueLevel = techniqueLevel;
    }

    public LevelsTAG getLevelTAG() {
        return levelTAG;
    }

    public void setLevelTAG(LevelsTAG levelTAG) {
        this.levelTAG = levelTAG;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int audioId;
    private String name;
    private String duration;
    private int age;
    private char schedule;
    private char auditory;
    private char gender;
    private int techniqueLevel;

    @ManyToOne
    private LevelsTAG levelTAG;

    // Getters and setters
}

