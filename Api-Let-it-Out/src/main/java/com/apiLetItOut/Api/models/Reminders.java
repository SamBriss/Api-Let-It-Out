package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "Reminders")
public class Reminders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int remindersId;
    private String name;
    private LocalTime time;
    private int active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userTAGId")
    private UsersTAG userTAG;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reminderTypeId")
    private ReminderTypes reminderType;


}
