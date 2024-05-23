package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.InformativeArticleRepository;

@Service
public class InformativeArticlesService {
    @Autowired 
    InformativeArticleRepository informativeArticleRepository;

    public List<Integer> SearchIdOfDocumentsMethod(String topic)
    {
        return informativeArticleRepository.SearchIdOfDocuments(topic);
    }

    public Integer SearchIdOfDocumentMethod(String topic)
    {
        return informativeArticleRepository.SearchIdOfDocument(topic);
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

    public List<String> SearchAllNamesOfInformationMethod()
    {
        return informativeArticleRepository.SearchAllNamesOfInformation();
    }

    public List<String> SearchNamesOfClassficationMethod(String topicClassification)
    {
        return informativeArticleRepository.SearchNamesOfClassfication(topicClassification);
    }
}
