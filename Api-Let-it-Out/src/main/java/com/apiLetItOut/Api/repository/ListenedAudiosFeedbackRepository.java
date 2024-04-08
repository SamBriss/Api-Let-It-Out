package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ListenedAudiosFeedback;

@Repository
public interface ListenedAudiosFeedbackRepository extends CrudRepository<ListenedAudiosFeedback, Integer>{
    @Query(value = "Select AVG(score) as scoreTechinique from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId", nativeQuery = true)
    Double searchScoreOfAudio (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);

    @Query(value = "Select audioId from listenedAudiosFeedback where userTAGId = :userTAGId and audioId = :audioId and score<3", nativeQuery = true)
    Integer SearchIdByScore (@Param("userTAGId") int userTAGId, @Param("audioId") int audioId);
}
