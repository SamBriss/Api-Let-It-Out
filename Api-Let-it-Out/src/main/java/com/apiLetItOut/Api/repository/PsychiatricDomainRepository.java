package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.PsychiatricDomainsQuestionaire;

import jakarta.transaction.Transactional;

@Repository
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
        
        @Transactional
        @Modifying
        @Query(value = "UPDATE psychiatricDomainsQuestionaire " + 
                        "SET score = :score, executionDate = :executionDate, " + "domainId = :domainId" +
                        "WHERE userTAGId = :userTAGId ", nativeQuery = true)
        int UpdateDomains(@Param("userTAGId") int userTAGId,
                                @Param("domainId") int domainId,
                                @Param("score") int score,
                                @Param("executionDate") Date executionDate);

        @Query (value = "Select domainId from psychiatricDomainsQuestionaire where userTAGId = :userTAGId ORDER BY score DESC", nativeQuery = true)
        List<Integer> SearchDomainsOfUserTAG(@Param("userTAGId") int userTAGId);

        @Query (value = "Select score from psychiatricDomainsQuestionaire where userTAGId = :userTAGId ORDER BY score DESC", nativeQuery = true)
        List<Integer> SearchScoresOfDomainId(@Param("userTAGId") int userTAGId);
}
