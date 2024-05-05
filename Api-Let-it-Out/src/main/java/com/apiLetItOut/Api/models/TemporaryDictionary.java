package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "TemporaryDictionary")
public class TemporaryDictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int temporaryDictionaryId;
    private String word;
    private int repetitions;

    @ManyToOne
    private DiccionaryCategories categoryId;

    public int getTemporaryDictionaryId() {
        return temporaryDictionaryId;
    }

    public void setTemporaryDictionaryId(int temporaryDictionaryId) {
        this.temporaryDictionaryId = temporaryDictionaryId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public DiccionaryCategories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(DiccionaryCategories categoryId) {
        this.categoryId = categoryId;
    }
}
