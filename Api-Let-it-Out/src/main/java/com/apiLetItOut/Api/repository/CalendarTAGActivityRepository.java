package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

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
    java.util.List<Object[]> findRegistersOfUserTagActivities(@Param("userTAGId") int userTAGId, @Param("date") Date date);

    @Query(value= "Select u.activityId, u.label, u.location, u.direction, u.date, u.startHour, u.endHour, u.dateRegister, u.comments, u.reminders FROM calendar_tag_activity u WHERE u.userTAGId=:userTAGId", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesFromCalendarTAG(@Param("userTAGId") int userTAGId);


    @Query(value= "Select u.activityId, u.label, u.location, u.direction, u.date, u.startHour, u.endHour, u.dateRegister, u.comments, u.reminders FROM calendar_tag_activity u WHERE u.userTAGId=:userTAGId AND u.date=:date", nativeQuery = true)
    java.util.List<Object[]> findAllActivitiesFromCalendarTAGByDate(@Param("userTAGId") int userTAGId, @Param("date") Date date);

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
                    
    @Query(value = "Select a.startHour, a.endHour, a.date from calendar_tag_activity a INNER JOIN usersTAG t ON a.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.date>=DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY a.startHour ASC", nativeQuery = true)
    List<Object[]> findAllActivitiesTAGAfterTodayDatesAndHours(@Param("username") String username);
}