package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

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

    @Query(value = "Select a.startHour, a.endHour, a.date from activity_therapist_calendar a INNER JOIN userstherapists t ON a.userTherapistId=t.userTherapistId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.date>=DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY a.startHour ASC", nativeQuery = true)
    List<Object[]> findActivitiesDatesAndHourTherapist(@Param("username") String username);
                    
}