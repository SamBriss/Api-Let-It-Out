package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table (name = "CalendarConfigurationUsers")
public class CalendarConfigurationUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int configurationId;

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public LocalTime getStartWorkDay() {
        return startWorkDay;
    }

    public void setStartWorkDay(LocalTime startWorkDay) {
        this.startWorkDay = startWorkDay;
    }

    public LocalTime getEndWorkDay() {
        return endWorkDay;
    }

    public void setEndWorkDay(LocalTime endWorkDay) {
        this.endWorkDay = endWorkDay;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    private LocalTime startWorkDay;
    private LocalTime endWorkDay;

    @ManyToOne
    private Users user;

    // Getters and setters
}
