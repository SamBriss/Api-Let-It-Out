package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.apiLetItOut.Api.models.FrequencyAlerts;

import jakarta.transaction.Transactional;

@Repository
public interface FrequencyAlertsRepository extends CrudRepository <FrequencyAlerts, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO frequecyalerts (userTAGId , userTherapistId) " +
    "VALUES (:userTAGId, :userTherapistId)", nativeQuery = true)
    Integer RegisterNewFrequencyAlerts(@Param("userTAGId") int userTAGId,
                    @Param("userTherapistId") int userTherapistId);
    @Transactional
    @Modifying
    @Query(value= "DELETE FROM frequecyalerts WHERE userTherapistId=:userTherapistId", nativeQuery = true)
    Integer DeletefrecuencyAlerts(@Param("userTherapistId") int userTherapistId); 
    @Query(value= "Select username FROM frequecyalerts f JOIN usersTAG ut ON ut.userTAGId=f.userTAGId JOIN users u ON u.userId=ut.userId WHERE f.userTherapistId=:userTherapistId", nativeQuery = true)
    List<String> SelectfrecuencyAlerts(@Param("userTherapistId") int userTherapistId);    
}
