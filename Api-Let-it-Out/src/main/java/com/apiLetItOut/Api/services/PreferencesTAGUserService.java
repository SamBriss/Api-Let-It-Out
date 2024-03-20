package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.PreferencestaguserRepository;

@Service
public class PreferencesTAGUserService {
    @Autowired
    PreferencestaguserRepository preferencestaguserRepository;
    
    public int PreferencesTAGUserMethod(String name, int score, int userTAGId, int categoryId){
        return preferencestaguserRepository.RegisterNewUserPreferences(name, score, userTAGId, categoryId);
    }
    
}
