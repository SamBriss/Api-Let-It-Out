package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

import com.apiLetItOut.Api.repository.AlgorithmTriggerElementsRepository;

@Service
public class AlgorithmTriggerElementsService {
    @Autowired
    AlgorithmTriggerElementsRepository algorithmTriggerElementsRepository;

    public List<Integer> getAllDictionaryWordsByCategoryAndUserTAGMethod(int userTAGId, int category)
    {
        return algorithmTriggerElementsRepository.getAllDictionaryWordsByCategoryAndUserTAG(userTAGId, category);
    }

    public Integer getRepetitionsAnxietyByUserTAGAndWordIdMethod(int userTAGId, int wordId)
    {
        return algorithmTriggerElementsRepository.getRepetitionsAnxietyByUserTAGAndWordId(userTAGId, wordId);
    }
    public int getRepetitionsTotalByUserTAGAndWordIdMethod(int userTAGId, int wordId)
    {
        return algorithmTriggerElementsRepository.getRepetitionsTotalByUserTAGAndWordId(userTAGId, wordId);
    }

    public List<Integer> getAllUserTAGIdMethod()
    {
        return algorithmTriggerElementsRepository.getAllUserTAGId();
    }
    public int addTriggerPatternMethod(Date date, int totalAttacks, int userTAGId, String img)
    {
        return algorithmTriggerElementsRepository.addTriggerPattern(date, totalAttacks, userTAGId, img);
    }
    public int AddTriggerElementMethod(int triggerPatternId, int dictionaryWordId, int count, double rPearson, int individualProbability)
    {
        return algorithmTriggerElementsRepository.AddTriggerElement(triggerPatternId, dictionaryWordId, count, rPearson, individualProbability);
    }

    public int selectCountTriggerPatternsMethod()
    {
        return algorithmTriggerElementsRepository.selectCountTriggerPatterns();
    }
    public java.sql.Date selectLastDateAlgorithmPatternsMethod()
    {
        return algorithmTriggerElementsRepository.selectLastDateAlgorithmPatterns();
    }

    public int selectCountPatternsMethod()
    {
        return algorithmTriggerElementsRepository.selectCountPatterns();
    }
    public int selectCountAttacksByUserTAGMethod(int userTAGId)
    {
        return algorithmTriggerElementsRepository.selectCountAttacksByUserTAG(userTAGId);
    }
    public int selectCountManualAttacksByUserTAGMethod(int userTAGId)
    {
        return algorithmTriggerElementsRepository.selectCountManualAttacksByUserTAG(userTAGId);
    }
}
