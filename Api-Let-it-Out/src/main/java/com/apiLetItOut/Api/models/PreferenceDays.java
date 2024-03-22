package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table (name = "PreferenceDays")
public class PreferenceDays {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preferenceDayId;
    private LocalTime StartHour;
    private LocalTime EndHour;
    private String label;

    @ManyToOne
    private CalendarConfigurationUsers configuration;

    @ManyToOne
    private WeekDays weekDay;

    public int getPreferenceDayId() {
        return preferenceDayId;
    }

    public void setPreferenceDayId(int preferenceDayId) {
        this.preferenceDayId = preferenceDayId;
    }

    public LocalTime getStartHour() {
        return StartHour;
    }

    public void setStartHour(LocalTime startHour) {
        StartHour = startHour;
    }

    public LocalTime getEndHour() {
        return EndHour;
    }

    public void setEndHour(LocalTime endHour) {
        EndHour = endHour;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public CalendarConfigurationUsers getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CalendarConfigurationUsers configuration) {
        this.configuration = configuration;
    }

    public WeekDays getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDays weekDay) {
        this.weekDay = weekDay;
    }

    // Getters and setters
}
