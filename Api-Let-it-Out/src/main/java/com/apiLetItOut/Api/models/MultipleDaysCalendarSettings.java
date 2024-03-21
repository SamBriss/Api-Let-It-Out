package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "MultipleDaysCalendarSettings")
public class MultipleDaysCalendarSettings {
    public int getMultipleDaysCalendarSettingsId() {
        return multipleDaysCalendarSettingsId;
    }

    public void setMultipleDaysCalendarSettingsId(int multipleDaysCalendarSettingsId) {
        this.multipleDaysCalendarSettingsId = multipleDaysCalendarSettingsId;
    }

    public CalendarConfigurationUsers getCalendarConfigurationUsers() {
        return configurationId;
    }

    public void setCalendarConfigurationUsers(CalendarConfigurationUsers configurationId) {
        this.configurationId = configurationId;
    }

    public WeekDays getWeekDayId() {
        return weekDayId;
    }

    public void setAudio(WeekDays weekDayId) {
        this.weekDayId = weekDayId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int multipleDaysCalendarSettingsId;

    @ManyToOne
    private CalendarConfigurationUsers configurationId;

    @ManyToOne
    private WeekDays weekDayId;

    // Getters and setters
}
