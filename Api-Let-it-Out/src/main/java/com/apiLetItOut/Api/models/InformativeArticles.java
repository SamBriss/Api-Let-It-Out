package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "InformativeArticles")
public class InformativeArticles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    private String name;
    private String URL;
    private String topicClassification;
    private String sinopsis;

    // Getter for articleId
    public int getArticleId() {
        return articleId;
    }

    // Setter for articleId
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for URL
    public String getURL() {
        return URL;
    }

    // Setter for URL
    public void setURL(String URL) {
        this.URL = URL;
    }

    // Getter for topicClassification
    public String getTopicClassification() {
        return topicClassification;
    }

    // Setter for topicClassification
    public void setTopicClassification(String topicClassification) {
        this.topicClassification = topicClassification;
    }

    // Getter for sinopsis
    public String getSinopsis() {
        return sinopsis;
    }

    // Setter for sinopsis
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}

