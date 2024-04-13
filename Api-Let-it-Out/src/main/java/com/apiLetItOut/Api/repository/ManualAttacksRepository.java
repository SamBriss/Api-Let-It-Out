package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ManualAttackRegister;

import jakarta.transaction.Transactional;

@Repository
public interface ManualAttacksRepository extends CrudRepository <ManualAttackRegister, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO manualattackregister" + 
    "(date, hour, place, motive, explanationResume," +
    "intensity, emotions, physicalSensations," +
    "thoughts, typeOfThought, attackMethodsId, userTAGId)" +
    "VALUES (:date, :hour, :place, :motive, :explanationResume," +
    ":intensity, :emotions, :physicalSensations, :thoughts, :typeOfThought," +
    ":attackMethodsId, :userTAGId)", nativeQuery = true)
    Integer RegisterNewManualAtttack(@Param("date") Date date,
                        @Param("hour") Date hour,
                        @Param("place") String place,
                        @Param("motive") String motive,
                        @Param("explanationResume") String explanationResume,
                        @Param("intensity") int intensity,
                        @Param("emotions") String emotions,
                        @Param("physicalSensations") String physicalSensations,
                        @Param("thoughts") String thoughts,
                        @Param("typeOfThought") String typeOfThought,
                        @Param("attackMethodsId") int attackMethodsId,
                        @Param("userTAGId") int userTAGId);
    
}
