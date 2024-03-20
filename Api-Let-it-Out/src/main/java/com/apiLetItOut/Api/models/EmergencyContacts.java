package com.apiLetItOut.Api.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "EmergencyContacts")
public class EmergencyContacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emergencyContactId;
    private String nameContact;

    public int getEmergencyContactId() {
        return emergencyContactId;
    }

    public void setEmergencyContactId(int emergencyContactId) {
        this.emergencyContactId = emergencyContactId;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public String getNumberContact() {
        return numberContact;
    }

    public void setNumberContact(String numberContact) {
        this.numberContact = numberContact;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    private String numberContact;

    @ManyToOne
    private UsersTAG userTAG;

    // Getters and setters
}
