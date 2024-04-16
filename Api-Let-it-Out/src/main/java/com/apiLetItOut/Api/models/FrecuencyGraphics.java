package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private LocalDate date;
    private int count;

    @ManyToOne
    private UsersTAG usersTAG;

    public UsersTAG getUsersTAG() {
        return usersTAG;
    }

    public void setUsersTAG(UsersTAG usersTAG) {
        this.usersTAG = usersTAG;
    }

    // Getters and setters
}

