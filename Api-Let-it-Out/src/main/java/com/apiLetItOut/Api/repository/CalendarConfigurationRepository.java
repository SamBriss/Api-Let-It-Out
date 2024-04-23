package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.CalendarConfigurationUsers;

import jakarta.transaction.Transactional;
public interface CalendarConfigurationRepository  extends CrudRepository <CalendarConfigurationUsers, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO CalendarConfigurationUsers " + 
            "(userId, startWorkDay, endWorkDay) " +
            "VALUES (:userId, :startWorkDay, :endWorkDay)", nativeQuery = true)
    Integer RegisterNewCalendarConfiguration(@Param("userId") int userId,
                        @Param("startWorkDay") Date startWorkDay,
                        @Param("endWorkDay") Date endWorkDay);

                        
    @Query(value= "Select configurationId FROM CalendarConfigurationUsers WHERE userId=:userId", nativeQuery = true)
    Integer SearchConfigurationIdByUserId (@Param("userId") int userId);

                         
    @Query(value= "Select c.startWorkDay FROM CalendarConfigurationUsers c INNER JOIN users u ON c.userId=u.userId INNER JOIN userstherapists t ON u.userId=t.userId WHERE u.username=:usernameTherapist", nativeQuery = true)
    Object SearchStartHourJourney (@Param("usernameTherapist") String usernameTherapist);
                         
    @Query(value= "Select c.endWorkDay FROM CalendarConfigurationUsers c INNER JOIN users u ON c.userId=u.userId INNER JOIN userstherapists t ON u.userId=t.userId WHERE u.username=:usernameTherapist", nativeQuery = true)
    Object SearchEndHourJourney (@Param("usernameTherapist") String usernameTherapist);

    //20-04-2024
    @Query(value = "Select c.configurationId, p.preferenceDayId, p.weekDayId, p.StartHour, p.EndHour, p.label, c.userId from preference_days p INNER JOIN calendarconfigurationusers c ON p.configurationId=c.configurationId INNER JOIN users u ON c.userId=u.userId WHERE u.username=:username", nativeQuery = true)
    List<Object[]> getPreferenceDaysUser(@Param("username") String username);

    @Query(value = "Select c.configurationId from preference_days p INNER JOIN calendarconfigurationusers c ON p.configurationId=c.configurationId INNER JOIN users u ON c.userId=u.userId WHERE u.username=:username", nativeQuery = true)
    int findConfigurationIdByUsername(@Param("username") String username);
     
    @Transactional
    @Modifying
    @Query(value = "Update preference_days set weekDayId =:weekDayId, StartHour =:startHour, EndHour =:endHour, label =:label where preferenceDayId=:preferenceDayId", nativeQuery = true)
    Integer UpdatePreferenceDays(@Param("preferenceDayId") int preferenceDayId, 
                        @Param("startHour") Date startHour,
                        @Param("endHour") Date endHour,
                        @Param("weekDayId") int weekDayId,
                        @Param("label") String label);

                        
    @Transactional
    @Modifying
    @Query(value = "Update calendarconfigurationusers set startWorkDay =:startHour, endWorkDay =:endHour where configurationId=:configurationId", nativeQuery = true)
    Integer UpdateWorkTimes(@Param("configurationId") int configurationId, 
    @Param("startHour") Date startHour, @Param("endHour") Date endHour);

}
