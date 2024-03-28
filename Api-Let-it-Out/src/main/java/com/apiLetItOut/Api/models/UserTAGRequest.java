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
@Table(name = "UsersTAGRequest")
public class UserTAGRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTAGRequestId;
    private int status;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private UsersTherapists usersTherapists;    
}
