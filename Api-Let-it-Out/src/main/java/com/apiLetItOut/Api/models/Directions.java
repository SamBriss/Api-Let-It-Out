package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Directions")
public class Directions {
    public int getDirectionId() {
        return directionId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumExt() {
        return numExt;
    }

    public void setNumExt(int numExt) {
        this.numExt = numExt;
    }

    public Integer getNumInt() {
        return numInt;
    }

    public void setNumInt(Integer numInt) {
        this.numInt = numInt;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int directionId;
    private String street;
    private int numExt;
    private int numInt; // Integer para permitir valores nulos
    private String colony;

    // Getters and setters
}

