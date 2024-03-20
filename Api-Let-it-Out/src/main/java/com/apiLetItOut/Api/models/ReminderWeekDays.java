package com.apiLetItOut.Api.models;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Table (name = "ReminderWeekDays")
public class ReminderWeekDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reminderWeekdayId;

    @ManyToOne
    private Reminders reminderId;

    @ManyToOne
    private WeekDays weekDayId;

    public int getReminderWeekdayId(){
        return reminderWeekdayId;
    }

    public void setReminderWeekdaysId(int reminderWeekdaysId){
        this.reminderWeekdayId=reminderWeekdaysId;
    }

    public Reminders getReminderId(){
        return reminderId;
    }

    public void setReminderId(Reminders reminderId){
        this.reminderId = reminderId;
    }

    public WeekDays getWeekDaysId(){
        return weekDayId;
    }

    public void setWeekDayId(WeekDays weekDayId){
        this.weekDayId=weekDayId;
    }
}

