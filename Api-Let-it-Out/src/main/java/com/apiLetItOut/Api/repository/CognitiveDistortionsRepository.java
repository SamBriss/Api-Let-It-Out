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
        "(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId, userTherapistId) " +
        "VALUES (:dateSituation, :thought, :physicalSensation, :emotionalFeeling, :consequence, :cognitiveDistortion, :userTAGId, :userTherapistId)", nativeQuery = true)
    Integer RegisterNewCognitiveDistortion(@Param("dateSituation") String dateSituation,
                    @Param("thought") String thought,
                    @Param("physicalSensation") String physicalSensation,
                    @Param("emotionalFeeling") String emotionalFeeling,
                    @Param("consequence") String consequence,
                    @Param("cognitiveDistortion") String cognitiveDistortion,
                    @Param("userTAGId") int userTAGId,
                    @Param("userTherapistId") int userTherapistId);

    @Query(value = "Select cognitiveDistortion from cognitivedistortions where userTAGId = :userTAGId", nativeQuery = true)
    List<String> SearchCongitiveDistortionsOfUser(@Param("userTAGId") int userTAGId);

    @Query(value = "SELECT COUNT(*) " +
                   "FROM cognitivedistortions " +
                   "WHERE userTherapistId = :userTherapistId", nativeQuery = true)
    int countByUserTherapistIdCognitiveDistortionsShared(@Param("userTherapistId") int userTherapistId);

    @Query(value = "SELECT cd.cognitiveDistortionId, cd.cognitiveDistortion, u.username " +
                "FROM cognitivedistortions cd " +
                "INNER JOIN usersTAG ut ON cd.userTAGId = ut.userTAGId " +
                "INNER JOIN users u ON ut.userId = u.userId " +
                "WHERE cd.userTherapistId = :userTherapistId", nativeQuery = true)
    List<Object[]> findCognitiveDistortionsShared(@Param("userTherapistId") int userTherapistId);

}
