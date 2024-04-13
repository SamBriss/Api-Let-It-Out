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

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table (name = "ActivitiesFromTherapist")
public class ActivitiesFromTherapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityTId;
    private String label;
    private String description;
    private LocalDate dateAsign;
    private LocalDate dateMax;
    private boolean completed;
    private String comments;
    private String document;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private UsersTherapists userTherapist;
}

