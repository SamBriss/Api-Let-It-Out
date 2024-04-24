package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FrequencyAlerts")
public class FrequencyAlerts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int FrequencyAlertsId;
    @ManyToOne
    private UsersTAG usersTAG;
    @ManyToOne
    private UsersTherapists usersTherapists;
    public int getFrequencyAlertsId() {
        return FrequencyAlertsId;
    }
    public void setFrequencyAlertsId(int frequencyAlertsId) {
        FrequencyAlertsId = frequencyAlertsId;
    }
    public UsersTAG getUsersTAG() {
        return usersTAG;
    }
    public void setUsersTAG(UsersTAG usersTAG) {
        this.usersTAG = usersTAG;
    }
    public UsersTherapists getUsersTherapists() {
        return usersTherapists;
    }
    public void setUsersTherapists(UsersTherapists usersTherapists) {
        this.usersTherapists = usersTherapists;
    }
    
}
