package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    public int getUserTAGRequestId() {
        return userTAGRequestId;
    }

    public void setUserTAGRequestId(int userTAGRequestId) {
        this.userTAGRequestId = userTAGRequestId;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
