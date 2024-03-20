package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "RegistryList")
public class RegistryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int listId;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DictionaryWords getDictionaryWord() {
        return dictionaryWord;
    }

    public void setDictionaryWord(DictionaryWords dictionaryWord) {
        this.dictionaryWord = dictionaryWord;
    }

    public UsersTAG getUserTAG() {
        return userTAG;
    }

    public void setUserTAG(UsersTAG userTAG) {
        this.userTAG = userTAG;
    }

    private LocalDate date;

    @ManyToOne
    private DictionaryWords dictionaryWord;

    @ManyToOne
    private UsersTAG userTAG;

    // Getters and setters
}
