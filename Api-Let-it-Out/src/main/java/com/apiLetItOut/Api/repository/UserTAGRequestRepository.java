package com.apiLetItOut.Api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.UserTAGRequest;

import jakarta.transaction.Transactional;

@Repository
public interface UserTAGRequestRepository extends CrudRepository<UserTAGRequest, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usertagrequest " + 
    "(userTAGId, status, userTherapistId) VALUES (:userTAGId, :status, :userTherapistId)", nativeQuery = true)
    Integer RegisterNewRequestUserTAG(@Param("userTAGId") int userTAGId,
                                    @Param("status") int status,
                                    @Param("userTherapistId") int userTherapistId);

    @Query(value= "Select userTAGId FROM usertagrequest WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    List<Integer> FoundIdUserTAGByTherapist(@Param("userTherapistId") int userTherapistId);

    @Query(value= "Select COUNT(*) FROM usertagrequest WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    Integer CountRequestQuantity(@Param("userTherapistId") int userTherapistId);

    @Transactional
    @Modifying
    @Query(value= "DELETE FROM usertagrequest WHERE userTAGId=:userTAGId AND userTherapistId=:userTherapistId", nativeQuery = true)
    Integer DeleteRequest(@Param("userTAGId") int userTAGId,
                            @Param("userTherapistId") int userTherapistId);                 
}
