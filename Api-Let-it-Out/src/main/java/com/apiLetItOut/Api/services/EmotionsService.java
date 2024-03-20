package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.EmotionsRepository;


@Service
public class EmotionsService {
    @Autowired
    EmotionsRepository emotionsRepository;

    public Integer SearchEmotionMethod( String emotion)
    {
        return emotionsRepository.SearchEmotion(emotion);
    }
    
}
