package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "TriggerPatterns")
public class TriggerPatterns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int triggerPatternId;

    private LocalDate date;
    private int totalAttacks;
    private String graphicImg;

    @ManyToOne
    private UsersTAG userTAG;
}

