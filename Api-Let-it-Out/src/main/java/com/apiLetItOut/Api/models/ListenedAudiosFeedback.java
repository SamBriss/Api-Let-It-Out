package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table (name = "ListenedAudiosFeedback")
public class ListenedAudiosFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int listenedId;
    private String progress;
    private int score;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private RelaxationTechniqueAudios audio;
}

