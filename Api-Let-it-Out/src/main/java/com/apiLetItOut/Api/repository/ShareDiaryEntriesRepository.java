package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ShareDiaryEntries;

import jakarta.transaction.Transactional;

@Repository
public interface ShareDiaryEntriesRepository extends CrudRepository<ShareDiaryEntries, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sharediaryentries " + 
    "(diaryId, userTherapistId) VALUES (:diaryId, :userTherapistId)", nativeQuery = true)
    Integer RegisterNewShareDiaryEntry(@Param("diaryId") int diaryId,
    @Param("userTherapistId") int userTherapistId);
    
    @Query(value = "SELECT de.diaryId, de.date, de.text, fe.name AS emotionName, u.username " +
                "FROM diaryentries de " +
                "JOIN sharediaryentries sde ON de.diaryId = sde.diaryId " +
                "JOIN usersTAG ut ON de.userTAGId = ut.userTAGId " +
                "JOIN users u ON ut.userId = u.userId " +
                "JOIN feedbackemotions fe ON de.emotionId = fe.emotionsId " +
                "WHERE sde.userTherapistId = :userTherapistId", nativeQuery = true)
    List<Object[]> findByUserTherapistId(@Param("userTherapistId") int userTherapistId);

    @Query(value = "SELECT COUNT(*) " +
                   "FROM sharediaryentries " +
                   "WHERE userTherapistId = :userTherapistId", nativeQuery = true)
    int countByUserTherapistId(@Param("userTherapistId") int userTherapistId);
}
