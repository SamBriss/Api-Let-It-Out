package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.PreferencestaguserRepository;

@Service
public class PreferencesTAGUserService {
    @Autowired
    PreferencestaguserRepository preferencestaguserRepository;
    
    public Integer PreferencesTAGUserMethod(String name, int score, int userTAGId, int categoryId){
        return preferencestaguserRepository.RegisterNewUserPreferences(name, score, userTAGId, categoryId);
    }

    public Integer SearchScoreOfPreferenceUserTAG(int userTAGId, String name)
    {
        return preferencestaguserRepository.SearchScoreOfPreferenceUserTAG(userTAGId, name);
    }

    public List<String> SearchPreferenceAuditive0ScoreMethod(int userTAGId, int categoryId)
    {
        return preferencestaguserRepository.SearchPreferenceAuditive0Score(userTAGId, categoryId);
    }

    public List<String> SearchLastPreferencesSensoriales(int userTAGId, int categoryId)
    {
        return preferencestaguserRepository.SearchLastPreferencesSensoriales(userTAGId, categoryId);
    }

    public String SearchLifeStylePreference(int userTAGId, int categoryId)
    {
        return preferencestaguserRepository.SearchLifeStylePreference(userTAGId, categoryId);
    }

    public String SearchPreferenceAuditivefavoriteMethod(int userTAGId, int categoryId)
    {
        return preferencestaguserRepository.SearchPreferenceAuditiveFavorite(userTAGId, categoryId);
    }
    
}
