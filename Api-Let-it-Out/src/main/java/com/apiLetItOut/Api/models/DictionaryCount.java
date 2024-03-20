package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "DictionaryCount")
public class DictionaryCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictionaryCountId;

    public int getDictionaryCountId() {
        return dictionaryCountId;
    }

    public void setDictionaryCountId(int dictionaryCountId) {
        this.dictionaryCountId = dictionaryCountId;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    public DictionaryWords getDictionaryWord() {
        return dictionaryWord;
    }

    public void setDictionaryWord(DictionaryWords dictionaryWord) {
        this.dictionaryWord = dictionaryWord;
    }

    private int repetitions;

    @ManyToOne
    private UsersTAG userTAG;

    @ManyToOne
    private DictionaryWords dictionaryWord;

    // Getters and setters
}
