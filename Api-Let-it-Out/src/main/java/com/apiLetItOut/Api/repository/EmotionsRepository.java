package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.FeedbackEmotions;

public interface EmotionsRepository extends CrudRepository <FeedbackEmotions, Integer>{ 

    @Query(value= "Select emotionsId FROM feedbackemotions WHERE name=:emotion", nativeQuery = true)
    Integer SearchEmotion(@Param("emotion") String emotion);

    @Query(value= "Select name FROM feedbackemotions WHERE emotionsId=:emotionsId", nativeQuery = true)
    String SearchEmotionName(@Param("emotionsId") int emotionsId);
}
