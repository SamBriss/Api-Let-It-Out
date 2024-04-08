package com.apiLetItOut.Api.models;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "RelaxationTechniqueAudios")
public class RelaxationTechniqueAudios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int audioId;
    private String name;
    private LocalTime duration;
    private char age;
    private char schedule;
    private char auditory;
    private char gender;
    private int techniqueLevel;
    private String preferenceSensorial;
    private String preferenceAuditive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "levelTAGId")
    private LevelsTAG levelTAG;
}

