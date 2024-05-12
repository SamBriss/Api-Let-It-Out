package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.util.List;

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

    public Integer RegisterTechniquesOfAttack(int userTAGId, int audioId, String progress, int score, LocalDate feedbackDate, Integer attackRegisterId)
    {
        return listenedAudiosFeedbackRepository.RegisterTechniqueListened(userTAGId, audioId, progress, score, feedbackDate, attackRegisterId);
    }

    public List<String> SearchUrlsOfTechniquesOfAttacksMethod(int attackRegisterId)
    {
        return listenedAudiosFeedbackRepository.SearchUrlsOfTechniquesOfAttacks(attackRegisterId);
    }

    public List<Object[]> SearchCountAverageOfTechnique(int usersTAGId)
    {
        return listenedAudiosFeedbackRepository.SearchCountAverageOfTechnique(usersTAGId);
    }

    public List<Integer> SearchAudiosIdListenedTAG(int userTAGId)
    {
        return listenedAudiosFeedbackRepository.SearchAudiosIdListenedTAG(userTAGId);
    }

    public List<Object[]> SearchInformationOfTechniqueIncompleteMethod(int attackRegisterId)
    {
        return listenedAudiosFeedbackRepository.SearchInformationOfTechniqueIncomplete(attackRegisterId);
    }

    public int SelectQuantityAudiosMethod(int attackRegisterId)
    {
        return listenedAudiosFeedbackRepository.SelectQuantityAudios(attackRegisterId);
    }

    public Integer UpdateCompletedfeedbackaudiosAttacks(int score, int listenedId)
    {
        return listenedAudiosFeedbackRepository.UpdateCompletedfeedbackaudiosAttacks(score, listenedId);
    }
}
