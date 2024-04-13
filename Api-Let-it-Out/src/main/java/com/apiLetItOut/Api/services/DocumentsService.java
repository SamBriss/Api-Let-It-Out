package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DocumentsRepository;

@Service
public class DocumentsService {
    @Autowired
    DocumentsRepository documentsRepository;
    public int RegisterNewActivityDocumentMethod(int activityTId, String referenceURL)
    {
        return documentsRepository.RegisterNewActvityDocument(activityTId, referenceURL);
    }
}
