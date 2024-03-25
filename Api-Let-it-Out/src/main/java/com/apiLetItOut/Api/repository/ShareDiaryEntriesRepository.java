package com.apiLetItOut.Api.repository;

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
    
}
