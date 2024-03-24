package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.CalendarTAGActivity;

import jakarta.transaction.Transactional;


@Repository
public interface CalendarTAGActivityRepository extends CrudRepository<CalendarTAGActivity, Integer> {
    
    // en esta no me capta startHour, dice que no existe sepa pq
    @Query(value= "Select u.startHour, u.endHour FROM calendar_tag_activity u WHERE u.userTAGId=:userTAGId AND u.date=:date", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfUserTagActivities(@Param("userTAGId") int userTherapistId, @Param("date") Date date);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO calendar_tag_activity (userTAGId, label, location, direction, date, startHour, endHour, dateRegister, comments, reminders)VALUES (:userTAGId, :label, :location, :direction, :date, :startHour, :endHour, :dateRegister, :comments, :reminders)", nativeQuery = true)
    Integer addNewActivityUserTagCalendar(@Param("userTAGId") int userTAGId,
                        @Param("label") String label,
                        @Param("location") String location,
                        @Param("direction") String direction,
                        @Param("date") Date date,
                        @Param("startHour") Date startHour,
                        @Param("endHour") Date endHour,
                        @Param("dateRegister") Date dateRegister,
                        @Param("comments") String comments,
                        @Param("reminders") int reminders);

                        
    @Query(value= "Select COUNT(*) FROM calendar_tag_activity", nativeQuery = true)
    int SearchCountActivityUserTagCalendar();
                    
}