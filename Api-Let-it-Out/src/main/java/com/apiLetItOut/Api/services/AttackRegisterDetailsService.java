package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.AttackRegisterDetailsRepository;
import com.apiLetItOut.Api.repository.AttackRegistersRepository;

@Service
public class AttackRegisterDetailsService {
    @Autowired 
    AttackRegisterDetailsRepository attackRegisterDetailsRepository;
    
    @Autowired
    AttackRegistersRepository attackRegistersRepository;

    public String SearchPlaceOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchPlaceOfAttack(attackRegisterId);
    }

    public String SearchMotiveOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchMotiveOfAttack(attackRegisterId);
    }

    public String SearchExplanationResumeOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchExplanationResumeOfAttack(attackRegisterId);
    }

    public Integer SearchIntensityOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchIntensityOfAttack(attackRegisterId);
    }

    public String SearchEmotionsOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchEmotionsOfAttack(attackRegisterId);
    }

    public String SearchPhysicalSensationsOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchPhysicalSensationsOfAttack(attackRegisterId);
    }

    public String SearchThoughtsOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchThoughtsOfAttack(attackRegisterId);
    }

    public String SearchtypeOfThoughtOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchtypeOfThoughtOfAttack(attackRegisterId);
    }

    public String SearchAttackMethodsOfAttackMethod(int attackRegisterId)
    {
        return attackRegisterDetailsRepository.SearchAttackMethodsOfAttack(attackRegisterId);
    }

    public List<Integer> SearchIntensitiesMethod(LocalDate actualDate, LocalDate beforeDate, int userTAGId)
    {
        return attackRegisterDetailsRepository.SearchIntensities(actualDate, beforeDate, userTAGId);
    }

    public Integer SearchCountOfTypeOfThoughtsMethod(LocalDate actualDate, LocalDate beforeDate, int userTAGId, char typeOfThought)
    {
        return attackRegisterDetailsRepository.SearchCountOfTypeOfThoughts(actualDate, beforeDate, userTAGId, typeOfThought);
    }

    public Integer SearchCountOfMethodsMethod(LocalDate actualDate, LocalDate beforeDate, int userTAGId, int attackMethodId)
    {
        return attackRegisterDetailsRepository.SearchCountOfMethods(actualDate, beforeDate, userTAGId, attackMethodId);
    }

    public Integer CountOfAttacksCompletedMethod(LocalDate actualDate, LocalDate beforeDate, int userTAGId)
    {
        return attackRegisterDetailsRepository.CountOfAttacksCompleted(actualDate, beforeDate, userTAGId);
    }

    public Integer RegisterNewAttackDetailsMethod( int attackRegisterId, String place, String motive, 
                    String explanationResume, int intensity, String emotions, String physicalSensations, 
                    String thoughts, String typeOfThought, int attackMethodsId, String reportURL)
    {
        return attackRegisterDetailsRepository.RegisterNewAtttackDetails(attackRegisterId, place, motive, explanationResume, intensity, emotions, physicalSensations, thoughts, typeOfThought, attackMethodsId, reportURL);
    }
}
