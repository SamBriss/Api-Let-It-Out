// Clase UsersTherapists
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

@Entity
@Table(name = "UsersTherapists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersTherapists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTherapistId;
    private String licence;
    private boolean contract;
    private int vinculationCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "userId")
    private Users userId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="directionId")
    private Directions directionId;

}
