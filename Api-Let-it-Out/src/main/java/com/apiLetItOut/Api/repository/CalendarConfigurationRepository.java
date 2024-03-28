package com.apiLetItOut.Api.repository;

import java.util.Date;

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

    
}
