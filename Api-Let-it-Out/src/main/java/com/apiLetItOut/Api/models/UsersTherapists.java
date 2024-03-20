// Clase UsersTherapists
package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UsersTherapists")
public class UsersTherapists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTherapistId;
    private String licence;
    private boolean contract;
    private int vinculationCode;

    @ManyToOne
    private Users user;

    public int getUserTherapistId() {
        return userTherapistId;
    }

    public void setUserTherapistId(int userTherapistId) {
        this.userTherapistId = userTherapistId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public boolean isContract() {
        return contract;
    }

    public void setContract(boolean contract) {
        this.contract = contract;
    }

    public int getVinculationCode() {
        return vinculationCode;
    }

    public void setVinculationCode(int vinculationCode) {
        this.vinculationCode = vinculationCode;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // Getters and setters
}
