package com.apiLetItOut.Api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.ManualAttacksRepository;

@Service
public class ManualAttacksService {
    @Autowired
    ManualAttacksRepository manualAttacksRepository;

    public Integer RegisterNewManualAttackMethod( Date date, Date hour, String place, String motive, 
                    String explanationResume, int intensity, String emotions, String physicalSensations, 
                    String thoughts, String typeOfThought, int attackMethodsId, int userTAGId)
    {
        return manualAttacksRepository.RegisterNewManualAtttack(date, hour, place, motive, 
        explanationResume, intensity, emotions, physicalSensations, thoughts, typeOfThought, 
        attackMethodsId, userTAGId);
    }
    
}
