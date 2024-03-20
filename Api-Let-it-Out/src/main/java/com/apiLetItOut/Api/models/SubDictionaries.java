package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "SubDictionaries")
public class SubDictionaries {
    public int getSubDiccionaryId() {
        return subDiccionaryId;
    }

    public void setSubDiccionaryId(int subDiccionaryId) {
        this.subDiccionaryId = subDiccionaryId;
    }

    public boolean isRepetition() {
        return repetition;
    }

    public void setRepetition(boolean repetition) {
        this.repetition = repetition;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subDiccionaryId;
    private boolean repetition;

    @ManyToOne
    private DictionaryWords dictionaryWord;

    @ManyToOne
    private UsersTAG userTAG;

    // Getters and setters
}
