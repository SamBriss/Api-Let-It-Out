package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.PsychiatricDomains;

public interface DomainRepository extends CrudRepository<PsychiatricDomains, Integer>{
    @Query(value = "Select name from PsychiatricDomains where domainId = :domainId", nativeQuery = true)
    String SearchNameOfDomain(@Param("domainId") int domainId);
}
