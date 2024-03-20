package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "WeekDays")
public class WeekDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int weekDayId;

    public int getWeekDayId() {
        return weekDayId;
    }

    public void setWeekDayId(int weekDayId) {
        this.weekDayId = weekDayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    // Getters and setters
}
