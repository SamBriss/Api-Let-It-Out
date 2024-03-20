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
@Table (name = "AttackRegisters")
public class AttackRegisters {
    public int getAttackRegisterId() {
        return attackRegisterId;
    }

    public void setAttackRegisterId(int attackRegisterId) {
        this.attackRegisterId = attackRegisterId;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public AttackRegisterTypes getType() {
        return type;
    }

    public void setType(AttackRegisterTypes type) {
        this.type = type;
    }

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attackRegisterId;
    private LocalDate date;
    private LocalTime startHour;
    private LocalTime endHour;
    private String duration;
    private boolean completed;

    @ManyToOne
    private AttackRegisterTypes type;

    // Getters and setters
}
