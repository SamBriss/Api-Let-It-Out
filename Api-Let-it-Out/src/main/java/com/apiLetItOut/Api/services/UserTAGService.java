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
}
