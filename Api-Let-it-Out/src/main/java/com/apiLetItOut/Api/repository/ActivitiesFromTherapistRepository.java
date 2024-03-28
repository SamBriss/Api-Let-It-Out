package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

import com.apiLetItOut.Api.models.ActivitiesFromTherapist;

import jakarta.transaction.Transactional;

@Repository
public interface ActivitiesFromTherapistRepository extends CrudRepository <ActivitiesFromTherapist, Integer>{
     @Transactional
    @Modifying
    @Query(value = "INSERT INTO activitiesfromtherapist " + 
        "(userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed) " +
        "VALUES (:userTAGId, :userTherapistId, :label, :description, :dateAsign, :dateMax, :completed)", nativeQuery = true)
    Integer RegisterNewActivityFromTherapist(@Param("userTAGId") int userTAGId,
                    @Param("userTherapistId") int userTherapistId,
                    @Param("label") String label,
                    @Param("description") String description,
                    @Param("dateAsign") Date dateAsign,
                    @Param("dateMax") Date dateMax,
                    @Param("completed") int completed);
    @Query(value= "Select COUNT(*) FROM activitiesfromtherapist WHERE userTAGId=:userTAGId", nativeQuery = true)
    Integer CountRequestQuantityActivities(@Param("userTAGId") int userTAGId);

    @Query(value= "Select activityTId FROM activitiesfromtherapist WHERE userTAGId=:userTAGId", nativeQuery = true)
    List<Integer> SelectActiivityId(@Param("userTAGId") int userTAGId);

    @Query(value= "Select userTherapistId FROM activitiesfromtherapist WHERE userTAGId=:userTAGId", nativeQuery = true)
    List<Integer> SelectTherapistId (@Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value = "Update activitiesfromtherapist set comments = :comments where activityTId =:activityTId", nativeQuery = true)
    Integer UpdateCommentsTherapist(@Param("activityTId") int activityTId,
    @Param("comments") String comments);
}

