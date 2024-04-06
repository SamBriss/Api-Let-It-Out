package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.RelationUsers;

import jakarta.transaction.Transactional;

@Repository
public interface RelationUsersRepository extends CrudRepository<RelationUsers, Integer>{
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO relationusers " + 
            "(userTAGId, userTherapistId) " +
            "VALUES (:userTAGId, :userTherapistId)", nativeQuery = true)
        Integer RegisterNewRelationUsers(@Param("userTAGId") int userTAGId,
                        @Param("userTherapistId") int userTherapistId);

        @Query(value= "Select userTherapistId FROM relationusers WHERE userTAGId=:userTAGId", nativeQuery = true)
        List<Integer> SearchTherapistByTAG (@Param("userTAGId") int userTAGId);

        @Query(value= "Select userTAGId FROM relationusers WHERE userTherapistId=:userTherapistId", nativeQuery = true)
        List<Integer> SearchTAGByTherapist (@Param("userTherapistId") int userTherapistId);

        @Query(value= "Select COUNT(*) FROM relationusers WHERE userTherapistId=:userTherapistId", nativeQuery = true)
        Integer CountRequestQuantityVinculation(@Param("userTherapistId") int userTherapistId);

        @Transactional
        @Modifying
        @Query(value= "DELETE FROM relationusers WHERE userTAGId=:userTAGId AND userTherapistId=:userTherapistId", nativeQuery = true)
        Integer DeleteVinculation(@Param("userTAGId") int userTAGId,
                                @Param("userTherapistId") int userTherapistId); 
        
        @Query (value="Select COUNT(*) from relationusers where userTAGId = :userTAGId", nativeQuery = true)
        Integer ExistenceOfUserTAGWithTherapist(@Param("userTAGId") int userTAGId);

        
        @Query(value= "Select u.name, u.lastnameP, u.username FROM relationusers r INNER JOIN userstherapists t ON r.userTherapistId=t.userTherapistId INNER JOIN users u ON t.userId=u.userId WHERE r.userTAGId=:userTAGId", nativeQuery = true)
        List<Object[]> SearchRelationTherapistsByUserTAGId (@Param("userTAGId") int userTAGId);

    }
