package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.PsychiatricDomainsQuestionaire;

import jakarta.transaction.Transactional;

public interface PsychiatricDomainRepository extends CrudRepository <PsychiatricDomainsQuestionaire, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO psychiatricDomainsQuestionaire " + 
            "(userTAGId, domainId, score, executionDate) " +
            "VALUES (:userTAGId, :domainId, :score, :executionDate)", nativeQuery = true)
    Integer RegisterNewDomains(@Param("userTAGId") int userTAGId,
                        @Param("domainId") int domainId,
                        @Param("score") int score,
                        @Param("executionDate") Date executionDate);

}
