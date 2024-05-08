package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.CognitiveDistortionsRepository;

@Service
public class CognitiveDistortionsService {
    @Autowired
    CognitiveDistortionsRepository cognitiveDistortionsRepository;

    public Integer RegisterNewCognitiveDistortionMethod( String dateSituation, String thought, String physicalSensation, String emotionalFeeling, String consequence, String cognitiveDistortion, int userTAGId, int userTherapistId)
    {
        return cognitiveDistortionsRepository.RegisterNewCognitiveDistortion(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId, userTherapistId);
    }

    public List<String> SearchCongitiveDistortionsOfUserMethod(int userTAGId)
    {
        return cognitiveDistortionsRepository.SearchCongitiveDistortionsOfUser(userTAGId);
    }

    public int countByUserTherapistIdCognitiveDistortionsSharedMethod(int userTherapistId) {
        return cognitiveDistortionsRepository.countByUserTherapistIdCognitiveDistortionsShared(userTherapistId);
    }

    public List<Object[]> findCognitiveDistortionsSharedMethod(int userTherapistId) {
        return cognitiveDistortionsRepository.findCognitiveDistortionsShared(userTherapistId);
    }
}
