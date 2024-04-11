package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ActivityTherapistCalendar;

import jakarta.transaction.Transactional;


@Repository
public interface ActivityTherapistCalendarRepository extends CrudRepository<ActivityTherapistCalendar, Integer> {
    
    // en esta no me capta startHour, dice que no existe sepa pq
    @Query(value= "Select u.startHour, u.endHour FROM activity_therapist_calendar u WHERE u.userTherapistId=:userTherapistId AND u.date=:date", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfTherapistActivities(@Param("userTherapistId") int userTherapistId, @Param("date") Date date);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO activity_therapist_calendar (date, startHour, endHour, motive, appointment, userTherapistId)VALUES (:date, :startHour, :endHour, :motive, :appointment, :userTherapistId)", nativeQuery = true)
    Integer addNewActivityTherapistCalendar(@Param("date") Date date,
                        @Param("startHour") Date startHour,
                        @Param("endHour") Date endHour,
                        @Param("motive") String motive,
                        @Param("appointment") int appointment,
                        @Param("userTherapistId") int userTherapistId);

                        
    @Query(value= "Select COUNT(*) FROM activity_therapist_calendar", nativeQuery = true)
    int SearchCountActivityTherapistCalendar ();
                    
}