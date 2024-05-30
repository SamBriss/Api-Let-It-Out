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
        "(userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed, document) " +
        "VALUES (:userTAGId, :userTherapistId, :label, :description, :dateAsign, :dateMax, :completed, :document)", nativeQuery = true)
    Integer RegisterNewActivityFromTherapist(@Param("userTAGId") int userTAGId,
                    @Param("userTherapistId") int userTherapistId,
                    @Param("label") String label,
                    @Param("description") String description,
                    @Param("dateAsign") Date dateAsign,
                    @Param("dateMax") Date dateMax,
                    @Param("completed") int completed,
                    @Param("document") String document);                                            
    @Query(value= "Select u.label, u.description, u.dateAsign, u.dateMax, t.name, t.lastnameP, t.username FROM activitiesfromtherapist u INNER JOIN userstherapists i ON u.userTherapistId=i.userTherapistId INNER JOIN users t ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND u.completed=0", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAG(@Param("userTAGId") int userTAGId);
    @Query(value= "Select u.label, u.description, u.dateAsign, u.dateMax, i.name, i.lastnameP, i.username FROM activitiesfromtherapist u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND u.dateMax=:date AND u.completed=0 ORDER BY(u.dateAsign)", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAGByDate(@Param("userTAGId") int userTAGId, @Param("date") Date date);
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
    @Query(value= "Select COUNT(*) FROM activitiesfromtherapist WHERE userTAGId=:userTAGId AND completed=:completed", nativeQuery = true)
    Integer CountRequestQuantityActivitiesCompleted(@Param("userTAGId") int userTAGId,
                                                    @Param("completed") int completed);
    @Query(value= "Select activityTId FROM activitiesfromtherapist WHERE userTAGId=:userTAGId AND completed=:completed", nativeQuery = true)
    List<Integer> SelectActiivityIdCompleted(@Param("userTAGId") int userTAGId, 
                                            @Param("completed") int completed);
    @Query(value= "Select userTherapistId FROM activitiesfromtherapist WHERE userTAGId=:userTAGId AND completed=:completed", nativeQuery = true)
    List<Integer> SelectTherapistIdCompleted (@Param("userTAGId") int userTAGId,
                                            @Param("completed") int completed);
    @Query(value= "Select dateMax FROM activitiesfromtherapist WHERE userTAGId=:userTAGId AND completed=:completed", nativeQuery = true)
    List<Date> SelectActivityDateCompleted (@Param("userTAGId") int userTAGId,
                                    @Param("completed") int completed);
    @Query(value= "Select dateMax FROM activitiesfromtherapist WHERE activityTId=:activityTId", nativeQuery = true)
    Date SelectdateMaxActivity (@Param("activityTId") int activityTId);
    @Query(value= "Select label FROM activitiesfromtherapist WHERE activityTId=:activityTId", nativeQuery = true)
    String SelectlabelActivity (@Param("activityTId") int activityTId);
    @Query(value= "Select description FROM activitiesfromtherapist WHERE activityTId=:activityTId", nativeQuery = true)
    String SelectdescriptionActivity (@Param("activityTId") int activityTId);
    @Query(value= "Select document FROM activitiesfromtherapist WHERE activityTId=:activityTId", nativeQuery = true)
    String SelectdocumentActivity (@Param("activityTId") int activityTId);
    @Transactional
    @Modifying
    @Query(value = "Update activitiesfromtherapist set completed = :completed where activityTId =:activityTId", nativeQuery = true)
    Integer UpdateCompleted(@Param("activityTId") int activityTId,
                            @Param("completed") int completed);
    @Query(value= "Select comments FROM activitiesfromtherapist WHERE activityTId=:activityTId", nativeQuery = true)
    String SelectcommentsActivity (@Param("activityTId") int activityTId);
}

