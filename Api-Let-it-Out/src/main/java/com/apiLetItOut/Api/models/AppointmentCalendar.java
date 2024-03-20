package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "AppointmentCalendar")
public class AppointmentCalendar {
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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

    public boolean isTherapistAcceptance() {
        return therapistAcceptance;
    }

    public void setTherapistAcceptance(boolean therapistAcceptance) {
        this.therapistAcceptance = therapistAcceptance;
    }

    public boolean isTAGacceptance() {
        return TAGacceptance;
    }

    public void setTAGacceptance(boolean TAGacceptance) {
        this.TAGacceptance = TAGacceptance;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public UsersTherapists getUserTherapist() {
        return userTherapist;
    }

    public void setUserTherapist(UsersTherapists userTherapist) {
        this.userTherapist = userTherapist;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentId;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;
    private boolean therapistAcceptance;
    private boolean TAGacceptance;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private UsersTherapists userTherapist;

    // Getters and setters
}

