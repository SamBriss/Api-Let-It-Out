package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "UsersTAG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersTAG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTAGId;
    private boolean diagnosticExistence;
    private boolean medsExistence;
    private LocalDate registerDate;
    private LocalDate levelTAGQuestionnaireDate;
    private int umbral;
    private int levelTechiniques;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private Users userId; 

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="levelTAG")
    private LevelsTAG levelTAG; 
}