package com.apiLetItOut.Api.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.Reminders;

import jakarta.transaction.Transactional;

@Repository
public interface RemindersRepository extends CrudRepository<Reminders, Integer>{

    @Transactional
    @Modifying
    @Query(value = "insert into reminders (userTAGId, name, time, reminderTypeId, active) values"
        + " (:userTAGId, :name, :time, :reminderType, :active)", nativeQuery = true)
    Integer InsertIntoReminders(@Param("userTAGId") int userTAGId,
                                @Param("name") String name,
                                @Param("time") LocalTime time,
                                @Param("reminderType") int reminderType,
                                @Param("active") int active);

    @Query(value = "Select remindersId from reminders where userTAGId=:userTAGId AND name = :name AND time = CONCAT('', :time, '') LIMIT 1", nativeQuery = true)
    Integer SearchReminderId(@Param("userTAGId") int userTAGId,
                            @Param("name") String name,
                            @Param("time") LocalTime time);

    @Transactional
    @Modifying
    @Query(value = "Insert into reminderweekdays (reminderId, weekdayId) VALUES (:reminderId, :weekdayId)", nativeQuery = true)
    Integer InsertIntoRemindersWeekdays(@Param("reminderId") int reminderId,
                                        @Param("weekdayId") int weekdayId);

    @Transactional
    @Modifying
    @Query(value = "Delete from reminderweekdays where reminderId = :reminderId", nativeQuery = true)
    Integer DeleteFromRemindersWeekDays(@Param("reminderId") int reminderId);

    @Transactional
    @Modifying
    @Query(value = "Delete from reminders where remindersId = :reminderId", nativeQuery = true)
    Integer DeleteFromReminders(@Param("reminderId") int reminderId);

    @Query(value = "Select name, time, active, remindersId from reminders where userTAGId = :userTAGId", nativeQuery = true)
    List<Object[]> SelectRemindersAllData(@Param("userTAGId") int userTAGId);

    @Query(value = "Select name, time, active, remindersId, reminderTypeId from reminders where remindersId = :reminderId", nativeQuery = true)
    Object[] SelectReminderData(@Param("reminderId") int reminderId);

    @Query(value="Select weekdayId from reminderweekdays where reminderId=:reminderId", nativeQuery = true)
    List<Integer> SelectDaysReminder(@Param("reminderId") int reminderId);

    @Query(value= "Select active from reminders where userTAGId = :userTAGId and name =:name", nativeQuery = true)
    Integer SelectActiveReminder(@Param("userTAGId") int userTAGId, @Param("name") String name);

    @Transactional
    @Modifying
    @Query(value= "Update reminders set active = :active where userTAGId = :userTAGId and name =:name", nativeQuery = true)
    Integer UpdateStatusReminderName(@Param("userTAGId") int userTAGId, @Param("name") String name, @Param("active")int active);

    @Transactional
    @Modifying
    @Query(value= "Update reminders set name = :name, reminderTypeId=:reminderTypeId, time=:time where remindersId= :reminderId", nativeQuery = true)
    Integer UpdateReminder(@Param("reminderId") int reminderId, 
                            @Param("name") String name, 
                            @Param("reminderTypeId")int reminderTypeId,
                            @Param("time") LocalTime time);
}