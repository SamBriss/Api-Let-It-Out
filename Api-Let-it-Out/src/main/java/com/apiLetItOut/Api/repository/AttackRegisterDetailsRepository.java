package com.apiLetItOut.Api.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.AttackRegisterDetails;

import jakarta.transaction.Transactional;

@Repository
public interface AttackRegisterDetailsRepository extends CrudRepository<AttackRegisterDetails, Integer>{
        @Query(value = "select place from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchPlaceOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select motive from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchMotiveOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select explanationResume from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchExplanationResumeOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select intensity from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        Integer SearchIntensityOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select emotions from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchEmotionsOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select physicalSensations from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchPhysicalSensationsOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select thoughts from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchThoughtsOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select typeOfThought from attackRegisterDetails where attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchtypeOfThoughtOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value = "select am.method from attacksMethods am JOIN attackRegisterDetails ar ON am.attackMethodId=ar.attackMethodId where ar.attackRegisterId = :attackRegisterId", nativeQuery = true)
        String SearchAttackMethodsOfAttack(@Param("attackRegisterId") int attackRegisterId);

        @Query(value="Select intensity from attackRegisterDetails ard JOIN attackregisters ar ON ard.attackRegisterId = ar.attackRegisterId " 
                + "where ar.date <= CONCAT('', :actualDate, '') AND ar.date>= CONCAT('', :beforeDate, '') AND ar.userTAGId = :userTAGId", nativeQuery=true)
        List<Integer> SearchIntensities(@Param("actualDate") LocalDate actualDate,
                                        @Param("beforeDate") LocalDate beforeDate,
                                        @Param("userTAGId") int userTAGId);

        @Query(value="Select count(*) from attackRegisterDetails ard JOIN attackregisters ar ON ard.attackRegisterId = ar.attackRegisterId" 
                + " where ar.date <= CONCAT('', :actualDate, '') AND ar.date>= CONCAT('', :beforeDate, '') AND ar.userTAGId = :userTAGId AND ard.typeOfThought = :typeOfThought", nativeQuery=true)
        Integer SearchCountOfTypeOfThoughts(@Param("actualDate") LocalDate actualDate,
                                        @Param("beforeDate") LocalDate beforeDate,
                                        @Param("userTAGId") int userTAGId,
                                        @Param("typeOfThought") char typeOfThought);
        @Query(value="Select count(*) from attackRegisterDetails ard JOIN attackregisters ar ON ard.attackRegisterId = ar.attackRegisterId" 
        + " where ar.date <= CONCAT('', :actualDate, '') AND ar.date>= CONCAT('', :beforeDate, '') AND ar.userTAGId = :userTAGId AND ard.attackMethodId = :attackMethodId", nativeQuery=true)
        Integer SearchCountOfMethods(@Param("actualDate") LocalDate actualDate,
                                        @Param("beforeDate") LocalDate beforeDate,
                                        @Param("userTAGId") int userTAGId,
                                        @Param("attackMethodId") int attackMethodId);
        @Query(value = "Select count(*) from attackRegisterDetails ard JOIN attackregisters ar ON ard.attackRegisterId = ar.attackRegisterId" +
                        " where ar.date<= CONCAT('', :actualDate, '') AND ar.date>= CONCAT('', :beforeDate, '') AND ar.userTAGId = :userTAGId", nativeQuery = true)
        Integer CountOfAttacksCompleted (@Param("actualDate") LocalDate actualDate,
                                        @Param("beforeDate") LocalDate beforeDate,
                                        @Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO attackregisterdetails" + 
    "(attackRegisterId, place, motive, explanationResume," +
    "intensity, emotions, physicalSensations," +
    "thoughts, typeOfThought, attackMethodsId, reportURL)" +
    "VALUES (:attackRegisterId, :place, :motive, :explanationResume," +
    ":intensity, :emotions, :physicalSensations, :thoughts, :typeOfThought," +
    ":attackMethodsId, :reportURL)", nativeQuery = true)
    Integer RegisterNewAtttackDetails(@Param("attackRegisterId") int attackRegisterId,
                        @Param("place") String place,
                        @Param("motive") String motive,
                        @Param("explanationResume") String explanationResume,
                        @Param("intensity") int intensity,
                        @Param("emotions") String emotions,
                        @Param("physicalSensations") String physicalSensations,
                        @Param("thoughts") String thoughts,
                        @Param("typeOfThought") String typeOfThought,
                        @Param("attackMethodsId") int attackMethodsId,
                        @Param("reportURL") String reportURL);
}
