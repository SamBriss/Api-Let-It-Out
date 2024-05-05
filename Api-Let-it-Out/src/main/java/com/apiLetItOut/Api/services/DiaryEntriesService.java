package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

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
    public Integer CountRequestQuantityDiaryMethod( int userTAGId)
    {
        return diaryEntriesRepository.CountRequestQuantityDiary(userTAGId);
    }
    public List<Integer> SelectDiaryIdMethod( int userTAGId)
    {
        return diaryEntriesRepository.SelectDiaryId(userTAGId);
    }
    public List<Date> SelectDiaryDateMethod( int userTAGId)
    {
        return diaryEntriesRepository.SelectDiaryDate(userTAGId);
    }
    public List<String> SelectDiaryTextMethod( int userTAGId)
    {
        return diaryEntriesRepository.SelectDiaryText(userTAGId);
    }
    public List<Integer> SelectDiaryEmotionsMethod( int userTAGId)
    {
        return diaryEntriesRepository.SelectDiaryEmotionId(userTAGId);
    }
    public List<Object[]> SearchDiariesEntriesMethod(int userTherapistId, int userTAGId)
    {
        return diaryEntriesRepository.SearchDiariesEntries(userTherapistId, userTAGId);
    }

    public String SearchTextOfDiaryEntryMethod(Integer diaryId)
    {
        return diaryEntriesRepository.SearchTextOfDiaryEntry(diaryId);
    }
}
