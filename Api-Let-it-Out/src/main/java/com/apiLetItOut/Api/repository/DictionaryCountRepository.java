package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.DictionaryCount;

import jakarta.transaction.Transactional;

@Repository
public interface DictionaryCountRepository extends CrudRepository <DictionaryCount, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dictionarycount (userTAGId, dictionaryWordId, repetitions, date, attackStatus) VALUES (:userTAGId, :dictionaryWordId, :repetitions, :date, :attackStatus)", nativeQuery=true)
    Integer RegisterNewDictionaryCount(@Param("userTAGId") int userTAGId,
                                @Param("dictionaryWordId") int dictionaryWordId, 
                                @Param("repetitions") int repetitions,
                                @Param("date") Date date,
                                @Param("attackStatus") int attackStatus);
    @Transactional
    @Modifying
    @Query(value = "UPDATE dictionarycount SET repetitions = repetitions + 1, date = CURDATE() WHERE dictionaryCountId = :dictionaryCountId", nativeQuery = true)
    Integer UpdateRepetitionsAndDate(@Param("dictionaryCountId") int dictionaryCountId);

    @Query(value = "SELECT dictionaryCountId FROM dictionarycount " +
            "WHERE userTAGId = :userTAGId AND dictionaryWordId = :dictionaryWordId AND date = :date", nativeQuery = true)
    Integer findCountIdByUserTagAndWordIdAndDate(int userTAGId, int dictionaryWordId, Date date);

}
