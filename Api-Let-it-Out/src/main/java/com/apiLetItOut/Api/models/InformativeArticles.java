package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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
    private int typeOfDocument;
    //1.-Libro
    //2.-Podcast
    //3.-Articulo
    //4.-Video
    
}

