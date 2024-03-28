package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;

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

                                            
    @Query(value= "Select u.label, u.description, u.dateAsign, u.dateMax, t.name, t.lastnameP, t.username FROM activitiesfromtherapist u INNER JOIN userstherapists i ON u.userTherapistId=i.userTherapistId INNER JOIN users t ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND u.completed=0", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAG(@Param("userTAGId") int userTAGId);

       
    @Query(value= "Select u.label, u.description, u.dateAsign, u.dateMax, i.name, i.lastnameP, i.username FROM activitiesfromtherapist u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND u.dateMax=:date AND u.completed=0", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAGByDate(@Param("userTAGId") int userTAGId, @Param("date") Date date);
        

}
