package com.apiLetItOut.Api.models;

import java.time.LocalDate;

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
@Table (name = "techniquesDownload")
public class TechniquesDownload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int techniquesDownloadId;
    private LocalDate dateDownload;
    @ManyToOne
    private UsersTAG userTAGId;
    @ManyToOne
    private RelaxationTechniqueAudios audioId;
}
