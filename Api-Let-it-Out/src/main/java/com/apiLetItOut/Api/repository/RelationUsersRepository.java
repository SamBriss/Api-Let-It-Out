package com.apiLetItOut.Api.repository;

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
}
