package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.UsersTherapists;

import jakarta.transaction.Transactional;

@Repository
public interface UserTherapistRepository extends CrudRepository <UsersTherapists, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO UsersTherapists (userId, licence, contract, vinculationCode, directionId) VALUES (:userId, :licence, :contract, :vinculationCode, :directionId)", nativeQuery = true)
    Integer RegisterNewUserTherapist(@Param(value="userId") int userId,
                        @Param("licence") String licence,
                        @Param("contract") boolean contract,
                        @Param("vinculationCode") int vinculationCode,
                        @Param("directionId") int directionId);

    @Query(value = "SELECT COUNT(*) FROM UsersTherapists WHERE userId=:userId", nativeQuery = true)
    Integer FindUserTherapists(@Param(value="userId") int userId);
}
