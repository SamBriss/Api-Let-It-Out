package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import com.apiLetItOut.Api.repository.AlgorithmRelaxationTechniquesRepository;

@Service
public class AlgorithmRelaxationTechniquesService {
 
    @Autowired
    AlgorithmRelaxationTechniquesRepository algorithmRelaxationTechniquesRepository;
    public int countRankingExitenceRegistersMethod()
    {
        return algorithmRelaxationTechniquesRepository.countRankingExitenceRegisters();
    }

    public Integer getLastCategoryIdMethod(int lastCount)
    {
        return algorithmRelaxationTechniquesRepository.getLastCategoryId(lastCount);
    }

    public int getCategoryIdFromLastRankingIdMethod()
    {
        return algorithmRelaxationTechniquesRepository.getCategoryIdFromLastRankingId();
    }

// get average score and repetitions then in the api controller of every single audio of the preference specified

    public java.util.List<Object[]>  getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getAverageScoreRepetitionsByAudioIdAndSensorialPreference(preference);
    }
    
    public java.util.List<Object[]>  getAverageScoreRepetitionsByAudioIdAndAuditivePreferenceMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getAverageScoreRepetitionsByAudioIdAndAuditivePreference(preference);
    }

    public java.util.List<Object[]>  getAverageScoreRepetitionsByAudioIdAndDurationMethod(int secondsMin, int secondsMax)
    {
        return algorithmRelaxationTechniquesRepository.getAverageScoreRepetitionsByAudioIdAndDuration(secondsMin, secondsMax);
    }
    
    public java.util.List<Object[]>  getAverageScoreRepetitionsByAudioIdAndLifestylePreferenceMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getAverageScoreRepetitionsByAudioIdAndLifestylePreference(preference);
    }

    public java.util.List<Object[]> getAverageScoreRepetitionsAudioIdByAgeUserMethod(int startAge, int endAge)
    {
        return algorithmRelaxationTechniquesRepository.getAverageScoreRepetitionsAudioIdByAgeUser(startAge, endAge);
    }

// repetitions services

    public Integer getCountOfAudioIdInFeedbacksMethod(int audioId)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfAudioIdInFeedbacks(audioId);
    }

// get count of registers in listenedaudiosfeedback by preference

    public int getCountOfFeedbacksMethod()
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacks();
    }
    
    public int getCountOfFeedbacksByPreferenceSensorialMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacksByPreferenceSensorial(preference);
    }

    public int getCountOfFeedbacksByPreferenceAuditiveMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacksByPreferenceAuditive(preference);
    }

    public int getCountOfFeedbacksByPreferenceAuditoryMethod(String preference)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacksByPreferenceAuditory(preference);
    }
    
    public int getCountOfFeedbacksByPreferenceDurationMethod(int secondsMin, int secondsMax)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacksByPreferenceDuration(secondsMin, secondsMax);
    }

    public int getCountOfFeedbacksByPreferenceAgeMethod(int startAge, int endAge)
    {
        return algorithmRelaxationTechniquesRepository.getCountOfFeedbacksByPreferenceAge(startAge, endAge);
    }

// add ranking
    public Integer addNewRankingPreferenceCategoryMethod(int audioId, double finalScore, int countUses, double usersScore, Date executionDate, int categoryId, String preferenceId)
    {
        return algorithmRelaxationTechniquesRepository.addNewRankingPreferenceCategory(audioId, finalScore, countUses, usersScore, executionDate, categoryId, preferenceId);
    }

    public Integer addNewRankingPreferenceCategorywithNullMethod(Date executionDate, int categoryId, String preferenceId)
    {
        return algorithmRelaxationTechniquesRepository.addNewRankingPreferenceCategorywithNull(executionDate, categoryId, preferenceId);
    }

    public Object selectLastExecutionDate()
    {
        return algorithmRelaxationTechniquesRepository.selectLastExecutionDate();
    }

    // 11-04-2024
    
    public Object[] getCategoryIdAndDateFromLastRankingIdMethod()
    {
        return algorithmRelaxationTechniquesRepository.getCategoryIdAndDateFromLastRankingId();
    }

    public Object getTechniqueOfTheWeekForEachUserMethod(String preference, Date date)
    {
        return algorithmRelaxationTechniquesRepository.getTechniqueOfTheWeekForEachUser(preference, date);
    }
    
    public Date getDateFromLastRankingIdMethod()
    {
        return algorithmRelaxationTechniquesRepository.getDateFromLastRankingId();
    }

    // 13-04-2024
    public int getCountByUserTAGMethod(String username)
    {
        return algorithmRelaxationTechniquesRepository.getCountByUserTAG(username);
    }
    public int UpdateCountRankingMethod(int userTAGId, int count)
    {
        return algorithmRelaxationTechniquesRepository.UpdateCountRanking(userTAGId, count);
    }
    public java.util.List<Integer> GetUserTAGIdFromCountRankingMethod()
    {
        return algorithmRelaxationTechniquesRepository.GetUserTAGIdFromCountRanking();
    }
}
