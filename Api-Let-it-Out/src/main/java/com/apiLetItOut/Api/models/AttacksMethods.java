package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AttacksMethods")
public class AttacksMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attacksMethodsId;
    private int methods;

    public int getAttacksMethodsId() {
        return attacksMethodsId;
    }

    public void setAttacksMethodsId(int attacksMethodsId) {
        this.attacksMethodsId = attacksMethodsId;
    }

    public int getMethods(){
        return methods;
    }

    public void setMethods(int methods){
        this.methods = methods;
    }
    
}
