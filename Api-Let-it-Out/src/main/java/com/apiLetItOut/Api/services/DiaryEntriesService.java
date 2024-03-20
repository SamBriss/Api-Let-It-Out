package com.apiLetItOut.Api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DiaryEntriesRepository;

@Service
public class DiaryEntriesService {
    @Autowired
    DiaryEntriesRepository diaryEntriesRepository;

    public Integer RegisterNewDiaryEntryMethod( Date date, Date hour, String text,  int userTAGId, int emocionId)
    {
        return diaryEntriesRepository.RegisterNewDiaryEntries(date, hour, text, userTAGId, emocionId);
    }
    
}
