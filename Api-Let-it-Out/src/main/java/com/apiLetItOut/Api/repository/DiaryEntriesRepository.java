package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.DiaryEntries;

import jakarta.transaction.Transactional;

@Repository
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

        @Query(value= "Select COUNT(*) FROM diaryEntries WHERE userTAGId=:userTAGId", nativeQuery = true)
        Integer CountRequestQuantityDiary(@Param("userTAGId") int userTAGId);

        @Query(value= "Select diaryId FROM diaryEntries WHERE userTAGId=:userTAGId ORDER BY date DESC", nativeQuery = true)
        List<Integer> SelectDiaryId (@Param("userTAGId") int userTAGId);

        @Query(value= "Select date FROM diaryEntries WHERE userTAGId=:userTAGId ORDER BY date DESC", nativeQuery = true)
        List<Date> SelectDiaryDate (@Param("userTAGId") int userTAGId);

        @Query(value= "Select text FROM diaryEntries WHERE userTAGId=:userTAGId ORDER BY date DESC", nativeQuery = true)
        List<String> SelectDiaryText (@Param("userTAGId") int userTAGId);

        @Query(value= "Select emotionId FROM diaryEntries WHERE userTAGId=:userTAGId ORDER BY date DESC", nativeQuery = true)
        List<Integer> SelectDiaryEmotionId (@Param("userTAGId") int userTAGId);

        @Query(value = "Select d.date, d.hour, d.diaryId from diaryEntries d join sharediaryentries sd on sd.diaryId= d.diaryId "
        + "where sd.userTherapistId = :userTherapistId AND d.userTAGId = :userTAGId", nativeQuery = true)
        List<Object[]> SearchDiariesEntries(@Param("userTherapistId") int userTherapistId,
                                            @Param("userTAGId") int userTAGId);
        
        @Query(value = " Select text from diaryEntries where diaryId = :diaryId", nativeQuery = true)
        String SearchTextOfDiaryEntry(@Param("diaryId") int diaryId);

        
    
}
