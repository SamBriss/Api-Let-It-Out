package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.CognitiveDistortions;

import jakarta.transaction.Transactional;

@Repository
public interface CognitiveDistortionsRepository extends CrudRepository <CognitiveDistortions, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cognitivedistortions " + 
        "(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId) " +
        "VALUES (:dateSituation, :thought, :physicalSensation, :emotionalFeeling, :consequence, :cognitiveDistortion, :userTAGId)", nativeQuery = true)
    Integer RegisterNewCognitiveDistortion(@Param("dateSituation") String dateSituation,
                    @Param("thought") String thought,
                    @Param("physicalSensation") String physicalSensation,
                    @Param("emotionalFeeling") String emotionalFeeling,
                    @Param("consequence") String consequence,
                    @Param("cognitiveDistortion") String cognitiveDistortion,
                    @Param("userTAGId") int userTAGId);

    @Query(value = "Select cognitiveDistortion from cognitivedistortions where userTAGId = :userTAGId", nativeQuery = true)
    List<String> SearchCongitiveDistortionsOfUser(@Param("userTAGId") int userTAGId);
}
