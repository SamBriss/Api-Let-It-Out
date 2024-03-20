package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "ActivityTherapistCalendar")
public class ActivityTherapistCalendar {
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public boolean isAppointment() {
        return appointment;
    }

    public void setAppointment(boolean appointment) {
        this.appointment = appointment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityId;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalTime duration;
    private String motive;
    private boolean appointment;

    // Getters and setters
}

