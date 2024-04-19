package com.apiLetItOut.Api.services;

import java.time.LocalDate;

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

    public String SearchMayorProgressMethod(int userTAGId, int audioId)
    {
        return listenedAudiosFeedbackRepository.SearchMayorProgress(userTAGId, audioId);
    }

    public Integer CountTimesAudioListenedMethod(int userTAGId, int audioId)
    {
        return listenedAudiosFeedbackRepository.CountTimesAudioListened(userTAGId, audioId);
    }

    public Integer RegisterTechniqueListenedMethod(int userTAGId, int audioId, String progress, int score, LocalDate feedbackDate)
    {
        int attackRegisterId = 1;
        return listenedAudiosFeedbackRepository.RegisterTechniqueListened(userTAGId, audioId, progress, score, feedbackDate, attackRegisterId);
    }
}
