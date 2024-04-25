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
    int FindUserTherapists(@Param(value="userId") int userId);

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

    @Query(value= "Select userId FROM userstherapists WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    Integer FoundUserIdByUserTherapist (@Param("userTherapistId") int userTherapistId);

    @Query(value = "Select userTherapistId from UsersTherapists where userId =:userId", nativeQuery = true)
    Integer SearchIdTherapistByUserId(@Param("userId") int userId);

    @Query(value = "Select licence From UsersTherapists where userTherapistId=:userTherapistId", nativeQuery = true)
    String SearchLicenceTherapist(@Param("userTherapistId") int userTherapistId);

    @Query(value = "Select directionId From UsersTherapists where userTherapistId=:userTherapistId", nativeQuery = true)
    Integer SearchDirectionIdTherapist(@Param("userTherapistId") int userTherapistId);

    @Transactional
    @Modifying
    @Query(value = "Update usersTherapists set licence = :licence where userTherapistId =:userTherapistId", nativeQuery = true)
    Integer UpdateLicenceTherapist(@Param("userTherapistId") int userTherapistId,
    @Param("licence") String licence);


    // 10-04-2024
    
    @Query(value = "Select u.name, u.lastnameP, u.username from users u INNER JOIN usersTherapists t ON u.userId=t.userId INNER JOIN appointmentcalendar a ON t.userTherapistId=a.userTherapistId WHERE a.appointmentId=:appointmentId", nativeQuery = true)
    Object[] FindNameLastNamePUsernameTherapistByTherapistId(@Param("appointmentId") int appointmentId);

    @Query(value = "Select t.userTherapistId from usersTherapists t INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username", nativeQuery = true)
    Integer findUserTherapistIdByUsername(@Param("username") String username);
}
