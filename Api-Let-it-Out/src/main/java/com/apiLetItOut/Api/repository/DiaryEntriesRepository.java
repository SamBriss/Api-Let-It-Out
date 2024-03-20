package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.DiaryEntries;

import jakarta.transaction.Transactional;


public interface DiaryEntriesRepository extends CrudRepository <DiaryEntries, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO diaryEntries " + 
            "(date, hour, text, userTAGId, emotionId) " +
            "VALUES (:date, :hour, :text, :userTAGId, :emotionId)", nativeQuery = true)
    Integer RegisterNewDiaryEntries(@Param("date") Date date,
                        @Param("hour") Date hour,
                        @Param("text") String text,
                        @Param("userTAGId") int userTAGId,
                        @Param("emotionId") int emotionId);

}
