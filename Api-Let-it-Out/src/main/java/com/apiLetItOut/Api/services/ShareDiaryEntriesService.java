package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.ShareDiaryEntriesRepository;

@Service
public class ShareDiaryEntriesService {
    @Autowired
    ShareDiaryEntriesRepository shareDiaryEntriesRepository;
    public Integer RegisterNewShareDiaryEntryMethod(int diaryId, int userTherapistId)
    {
        return shareDiaryEntriesRepository.RegisterNewShareDiaryEntry(diaryId, userTherapistId);
    }
}
