package com.apiLetItOut.Api.services;

import java.util.Date;

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
        return psychiatricDomainRepository.updateDomains(userTAGId, domainId, score, executionDate);
    }
}
