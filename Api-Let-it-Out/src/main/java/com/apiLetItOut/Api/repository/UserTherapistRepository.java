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

    @Query(value = "Select COUNT(*) FROM UsersTherapists WHERE vinculationCode=:vinculationCode", nativeQuery = true)
    int SearchTherapistCode (@Param("vinculationCode") int vinculationCode);

    @Query(value = "Select userTherapistId FROM UsersTherapists WHERE userId=:userId", nativeQuery = true)
    Integer FoundIdUserTherapist (@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE userstherapists SET vinculationCode=:vinculationCode WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    Integer updateTherapistCode (@Param("userTherapistId") int userTherapistId,
                        @Param("vinculationCode") int vinculationCode);

    @Query(value = "Select userTherapistId FROM UsersTherapists WHERE vinculationCode=:vinculationCode", nativeQuery = true)
    int SearchTherapistIdByCode (@Param("vinculationCode") int vinculationCode);

    @Query(value = "Select vinculationCode FROM UsersTherapists WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    int SearchTherapistExistanceCode (@Param("userTherapistId") int userTherapistId);
}
