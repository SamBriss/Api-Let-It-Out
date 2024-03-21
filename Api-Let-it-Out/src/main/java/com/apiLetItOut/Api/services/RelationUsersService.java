package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.RelationUsersRepository;

@Service
public class RelationUsersService {
    @Autowired
    RelationUsersRepository relationUsersRepository;
    public int RegisterNewRelationUsersMethod(int userTAGId, int userTherapistId)
    {
        return relationUsersRepository.RegisterNewRelationUsers(userTAGId, userTherapistId);
    }
    
    
}
