package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public int isAppointment() {
        return appointment;
    }

    public void setAppointment(int appointment) {
        this.appointment = appointment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityId;
    private Date date;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalTime duration;
    private String motive;
    private int appointment;
    
    @ManyToOne
    private UsersTherapists userTherapistId;

    public UsersTherapists getUserTherapistId() {
        return userTherapistId;
    }

    public void setUserTherapistId(UsersTherapists userTherapistId) {
        this.userTherapistId = userTherapistId;
    }

    // Getters and setters
}

