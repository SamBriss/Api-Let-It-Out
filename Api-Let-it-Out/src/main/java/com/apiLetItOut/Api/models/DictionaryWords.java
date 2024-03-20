package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "DictionaryWords")
public class DictionaryWords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dictionaryWordId;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDictionaryWordId() {
        return dictionaryWordId;
    }

    public void setDictionaryWordId(int dictionaryWordId) {
        this.dictionaryWordId = dictionaryWordId;
    }

    public DiccionaryCategories getCategory() {
        return category;
    }

    public void setCategory(DiccionaryCategories category) {
        this.category = category;
    }

    private String word;

    @ManyToOne
    private DiccionaryCategories category;

    // Getters and setters
}

