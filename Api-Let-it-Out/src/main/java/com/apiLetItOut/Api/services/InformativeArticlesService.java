package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.InformativeArticleRepository;

@Service
public class InformativeArticlesService {
    @Autowired 
    InformativeArticleRepository informativeArticleRepository;

    public int SearchIdOfDocumentMethod(String topic)
    {
        String topicFormated = topic.toLowerCase();
        return informativeArticleRepository.SearchIdOfDocument(topicFormated);
    }

    public String SearchNameOfDocumentMethod(int articleId)
    {
        return informativeArticleRepository.SearchNameOfDocument(articleId);
    }

    public String SearchUrlOfDocumentMethod(int articleId)
    {
        return informativeArticleRepository.SearchUrlOfDocument(articleId);
    }

    public String SearchClassificationOfDocumentMethod(int articleId)
    {
        return informativeArticleRepository.SearchClassificationOfDocument(articleId);
    }

    public String SearchSinopsisOfDocumentMethod(int articleId)
    {
        return informativeArticleRepository.SearchSinopsisOfDocument(articleId);
    }

    public int SearchTypeOfDocumentMethod(int articleId)
    {
        return informativeArticleRepository.SearchTypeOfDocument(articleId);
    }
}
