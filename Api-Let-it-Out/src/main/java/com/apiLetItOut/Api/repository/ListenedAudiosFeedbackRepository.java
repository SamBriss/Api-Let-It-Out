package com.apiLetItOut.Api.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ListenedAudiosFeedback;

import jakarta.transaction.Transactional;

@Repository
public interface ListenedAudiosFeedbackRepository extends CrudRepository<ListenedAudiosFeedback, Integer>{
    @Query(value = "Select AVG(score) as scoreTechinique from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId", nativeQuery = true)
    Double searchScoreOfAudio (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);

    @Query(value = "Select audioId from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId and score<3", nativeQuery = true)
    Integer SearchIdByScore (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);

    @Query(value = "Select progress from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId LIMIT 1", nativeQuery = true)
    String SearchMayorProgress (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);

    @Query (value = "Select count(*) from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId", nativeQuery = true)
    Integer CountTimesAudioListened (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO listenedAudiosFeedback (userTAGId, audioId, progress, score, feedbackdate, attackRegisterId)"+
    "VALUES (:userTAGId, :audioId, :progress, :score, :feedbackDate, :attackRegisterId)", nativeQuery = true)
    Integer RegisterTechniqueListened(@Param("userTAGId") int userTAGId,
                                        @Param("audioId") int audioId,
                                        @Param("progress") String progress,
                                        @Param("score") int score, 
                                        @Param("feedbackDate") LocalDate feedbackDate,
                                        @Param("attackRegisterId") int attackRegisterId);
}
