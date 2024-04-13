package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.ListenedAudiosFeedbackRepository;

@Service
public class ListenedAudiosFeedbackService {
    @Autowired
    ListenedAudiosFeedbackRepository listenedAudiosFeedbackRepository;
    
    public Double SearchScoreOfAudioMethod(int userTAGId, int audioId)
    {
        return listenedAudiosFeedbackRepository.searchScoreOfAudio(userTAGId, audioId);
    }

    public Integer SearchIdByScore(int userTAGId, int audioId)
    {
        return listenedAudiosFeedbackRepository.SearchIdByScore(userTAGId, audioId);
    }
}
