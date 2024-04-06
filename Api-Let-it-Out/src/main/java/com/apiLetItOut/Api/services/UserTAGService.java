package com.apiLetItOut.Api.services;

import java.util.Date;

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
    public Integer GetUserTAGIdByeUsernameMethod(String username)
    {
        return userTAGRepository.GetUserTAGIdByeUsername(username);
    }
}
