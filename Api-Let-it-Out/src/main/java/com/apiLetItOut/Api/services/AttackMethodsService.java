package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.AttackMethodsRepository;

@Service
public class AttackMethodsService {
    @Autowired
    AttackMethodsRepository attackMethodsRepository;
    public String SearchNameMethod(int attackMethodId)
    {
        return attackMethodsRepository.SearchNameMethod(attackMethodId);
    }
}
