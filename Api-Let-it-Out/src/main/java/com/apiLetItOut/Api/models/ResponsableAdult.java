package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "ResponsableAdult")
public class ResponsableAdult {
    public int getAdultId() {
        return adultId;
    }

    public void setAdultId(int adultId) {
        this.adultId = adultId;
    }

    public String getNameResponsable() {
        return nameResponsable;
    }

    public void setNameResponsable(String nameResponsable) {
        this.nameResponsable = nameResponsable;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adultId;
    private String nameResponsable;
    private String parentesco;

    @ManyToOne
    private UsersTAG userTAG;

    // Getters and setters
}

