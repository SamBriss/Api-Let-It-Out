package com.apiLetItOut.Api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.TechniquesDownloadRepository;

@Service
public class TechniquesDownloadService {
    @Autowired
    TechniquesDownloadRepository techniquesDownloadRepository;
    public Integer deleteDownloads(int userTAGId)
    {
        return techniquesDownloadRepository.deleteDownloads(userTAGId);
    }
    public Integer InsertDownloads(LocalDate date, int userTAGId, int audioId, int completed)
    {
        return techniquesDownloadRepository.InsertDownloads(date, userTAGId, audioId, completed);
    }
    public Integer CheckCompleteMethod(int userTAGId, LocalDate dateDownload)
    {
        return techniquesDownloadRepository.CheckComplete(dateDownload, userTAGId);
    }
}
