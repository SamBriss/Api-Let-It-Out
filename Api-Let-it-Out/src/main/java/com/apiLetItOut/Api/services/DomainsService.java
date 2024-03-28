package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DomainRepository;

@Service
public class DomainsService {
    @Autowired
    DomainRepository domainRepository;

    public String SearchNameOfDomain(int domainId)
    {
        return domainRepository.SearchNameOfDomain(domainId);
    }

}
