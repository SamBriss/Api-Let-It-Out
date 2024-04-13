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
@Table (name = "preference_days")
public class PreferenceDays {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preferenceDayId;
    private LocalTime StartHour;
    private LocalTime EndHour;
    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "configurationId")
    private CalendarConfigurationUsers configuration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weekDayId")
    private WeekDays weekDay;

}
