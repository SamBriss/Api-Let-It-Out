package com.apiLetItOut.Api.models;

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
@Table (name = "count_ranking_technique_by_user")
public class CountRankingTechniqueByUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countId;
    private int count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userTAGId")
    private UsersTAG userTAGId;


}
