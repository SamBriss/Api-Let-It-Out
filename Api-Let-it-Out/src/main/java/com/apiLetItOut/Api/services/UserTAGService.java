package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.UserTAGRepository;

@Service
public class UserTAGService {
    @Autowired
    UserTAGRepository userTAGRepository;
    public Integer FindUserTAGMethod(int userId)
    {
        return userTAGRepository.countUsersByUserId(userId);
    }

    public Integer RegisterNewUserTAGMethod(int userId, int levelTAGId, int medsExistence, Date registerDate, Date levelTAGQuestionaireDate, int umbral, int levelTechiniques)
    {
        return userTAGRepository.RegisterNewUserTAG(userId, levelTAGId, medsExistence, registerDate, levelTAGQuestionaireDate, umbral, levelTechiniques);
    }

    public Integer FoundTAGMethod(int userId)
    {
        return userTAGRepository.FoundIdUserTag(userId);
    }

    public Integer FoundIdByTAGMethod(int userTAGId)
    {
        return userTAGRepository.FoundUserIdByUserTAG(userTAGId);
    }

    public Integer SearchLevelTAGMethod(int userTAGId)
    {
        return userTAGRepository.SearchLevelTAG(userTAGId);
    }

    public Boolean SearchMedsExistenceTAG(int userTAGId)
    {
        return userTAGRepository.SearchMedsExistenceTAG(userTAGId);
    }

    public Integer UpdateUserTAGLevelMedsExistenceMethod(int levelTAGId, int medsExistence, int userTAGId, int umbral)
    {
        return userTAGRepository.UpdateUserTAGLevelMedsExistence(levelTAGId, medsExistence, umbral, userTAGId);
    }

    public List<Integer> SearchUsersSimilarsId(int age, int levelTAGId)
    {
        int bottomLimitAge=0, topLimitAge=0;
        if(age>7 && age<14)
        {
            bottomLimitAge = 8;
            topLimitAge = 13;
        } else if(age>13 && age<21)
        {
            bottomLimitAge = 14;
            topLimitAge =20;
        } else if(age > 20 && age<36)
        {
            bottomLimitAge = 21;
            topLimitAge = 35;
        } else if(age>35 && age<60)
        {
            bottomLimitAge =36;
            topLimitAge = 59;
        } else if(age>59)
        {
            bottomLimitAge =60;
            topLimitAge=99;
        }

        return userTAGRepository.SearchUsersSimilarsId(bottomLimitAge, topLimitAge, levelTAGId);
    }

    public List<String> SearchAllUsernameOfUsersTAGMethod()
    {
        return userTAGRepository.SearchAllUsersTAG();
    }

}
