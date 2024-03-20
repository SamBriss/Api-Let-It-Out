package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalTime;

@Entity
@Table (name = "Reminders")
public class Reminders {
    public int getRemindersId() {
        return remindersId;
    }

    public void setRemindersId(int remindersId) {
        this.remindersId = remindersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public ReminderTypes getReminderType() {
        return reminderType;
    }

    public void setReminderType(ReminderTypes reminderType) {
        this.reminderType = reminderType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int remindersId;
    private String name;
    private LocalTime time;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private ReminderTypes reminderType;


}
