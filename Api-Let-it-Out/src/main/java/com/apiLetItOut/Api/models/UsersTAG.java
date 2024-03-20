package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "UsersTAG")
public class UsersTAG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTAGId;
    private boolean diagnosticExistence;
    private boolean medsExistence;
    private LocalDate registerDate;
    private LocalDate levelTAGQuestionnaireDate;
    private int umbral;

    @ManyToOne
    private Users user; // Assuming Users is the entity for the Users table

    public int getUserTAGId() {
        return userTAGId;
    }

    public void setUserTAGId(int userTAGId) {
        this.userTAGId = userTAGId;
    }

    public int getUmbral(){
        return umbral;
    }

    public void setUmbral(int umbral){
        this.umbral = umbral;
    }

    public boolean isDiagnosticExistence() {
        return diagnosticExistence;
    }

    public void setDiagnosticExistence(boolean diagnosticExistence) {
        this.diagnosticExistence = diagnosticExistence;
    }

    public boolean isMedsExistence() {
        return medsExistence;
    }

    public void setMedsExistence(boolean medsExistence) {
        this.medsExistence = medsExistence;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getLevelTAGQuestionnaireDate() {
        return levelTAGQuestionnaireDate;
    }

    public void setLevelTAGQuestionnaireDate(LocalDate levelTAGQuestionnaireDate) {
        this.levelTAGQuestionnaireDate = levelTAGQuestionnaireDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LevelsTAG getLevelTAG() {
        return levelTAG;
    }

    public void setLevelTAG(LevelsTAG levelTAG) {
        this.levelTAG = levelTAG;
    }

    @ManyToOne
    private LevelsTAG levelTAG; // Assuming LevelsTAG is the entity for the LevelsTAG table

    
}