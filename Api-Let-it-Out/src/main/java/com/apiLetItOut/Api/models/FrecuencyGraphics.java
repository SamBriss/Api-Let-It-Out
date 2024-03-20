package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "FrecuencyGraphics")
public class FrecuencyGraphics {
    public int getFrecuencyGraphicId() {
        return frecuencyGraphicId;
    }

    public void setFrecuencyGraphicId(int frecuencyGraphicId) {
        this.frecuencyGraphicId = frecuencyGraphicId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int frecuencyGraphicId;
    private String URL;
    private String elapsedTime;
    private LocalDate date;
    private int count;

    // Getters and setters
}

