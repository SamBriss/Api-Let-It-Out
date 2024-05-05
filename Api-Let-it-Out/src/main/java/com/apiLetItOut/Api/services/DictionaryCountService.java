package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DictionaryCountRepository;

@Service
public class DictionaryCountService {
    @Autowired
    DictionaryCountRepository dictionaryCountRepository;
    public Integer RegisterNewDictionaryCountMethod( int userTAGId, int dictionaryWordId, int repetitions,  Date currentDate, int attackStatus)
    {
        return dictionaryCountRepository.RegisterNewDictionaryCount(userTAGId, dictionaryWordId, repetitions, currentDate, attackStatus);
    }
    public Integer UpdateRepetitionsAndDateMethod( int dictionaryCountId){
        return dictionaryCountRepository.UpdateRepetitionsAndDate(dictionaryCountId);
    }
    public Integer findCountIdByUserTagAndWordIdAndDateMethod(int userTAGId, int dictionaryWordId, Date date){
        return dictionaryCountRepository.findCountIdByUserTagAndWordIdAndDate(userTAGId, dictionaryWordId, date);
    }
    
}
