package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.util.List;

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
    public List<Integer> SearchAudioIdByDateMethod(int userTAGId, LocalDate dateDownload)
    {
        return techniquesDownloadRepository.SearchAudioIdByDate(dateDownload, userTAGId);
    }
    public boolean CheckCompleteAudioId(int userTAGId, LocalDate dateDownload, int audioId)
    {
        return techniquesDownloadRepository.CheckCompleteAudioId(dateDownload, userTAGId, audioId);
    }
    public List<String> SearchUrlOdAudiosDownloaded(int userTAGId)
    {
        return techniquesDownloadRepository.SearchAudiosDownloaded(userTAGId);
    }
    public List<String> SearchUrlOdAudiosToDownloadMethod(int userTAGId, LocalDate dateDownload)
    {
        return techniquesDownloadRepository.SearchAudiosToDownload(userTAGId, dateDownload);
    }
}
