package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.TemporaryDictionaryRepository;

@Service
public class TemporaryDictionaryService {
    @Autowired
    TemporaryDictionaryRepository temporaryDictionaryRepository;

    public Integer RegisterNewTemporaryWord(String word, int categoryId, int repetitions)
    {
        return temporaryDictionaryRepository.RegisterNewTemporaryWord(word, categoryId, repetitions);
    }

    public Integer SelectTemporaryIdMethod(String word, int categoryId)
    {
        return temporaryDictionaryRepository.SelectTemporaryId(word, categoryId);
    }

    public Integer DeleteTemporaryWord(int temporaryDictionaryId)
    {
        return temporaryDictionaryRepository.DeleteTemporaryWord(temporaryDictionaryId);
    }
    
}
