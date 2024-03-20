package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "RelationUsers")
public class RelationUsers {
    public int getRelationUsersId() {
        return relationUsersId;
    }

    public void setRelationUsersId(int relationUsersId) {
        this.relationUsersId = relationUsersId;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public UsersTherapists getUserTherapist() {
        return userTherapist;
    }

    public void setUserTherapist(UsersTherapists userTherapist) {
        this.userTherapist = userTherapist;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int relationUsersId;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private UsersTherapists userTherapist;

    // Getters and setters
}

