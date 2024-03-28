package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.PsychiatricDomainRepository;

@Service
public class PsychiatricDomainService {
    @Autowired
    PsychiatricDomainRepository psychiatricDomainRepository;
    public Integer RegisterNewDomainsMethod( int userTAGId,int domainId, int score, Date executionDate)
    {
        return psychiatricDomainRepository.RegisterNewDomains(userTAGId, domainId, score, executionDate);
    }
    public Integer UpdateDomainsMethod(int userTAGId, int domainId, int score, Date executionDate)
    {
        return psychiatricDomainRepository.UpdateDomains(userTAGId, domainId, score, executionDate);
    }
    public List<Integer> SearchDomainsOfUserTAGMethod(int userTAGId)
    {
        return psychiatricDomainRepository.SearchDomainsOfUserTAG(userTAGId);
    }
    public List<Integer> SearchScoreOfDomainsId (int userTAGId)
    {
        return psychiatricDomainRepository.SearchScoresOfDomainId(userTAGId);
    }
}
