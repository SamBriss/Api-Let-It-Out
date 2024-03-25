package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.CognitiveDistortionsRepository;

@Service
public class CognitiveDistortionsService {
    @Autowired
    CognitiveDistortionsRepository cognitiveDistortionsRepository;

    public Integer RegisterNewCognitiveDistortionMethod( String dateSituation, String thought, String physicalSensation, String emotionalFeeling, String consequence, String cognitiveDistortion, int userTAGId)
    {
        return cognitiveDistortionsRepository.RegisterNewCognitiveDistortion(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId);
    }
}
