package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.MultipleDaysCalendarSettings;
import java.util.List;
import jakarta.transaction.Transactional;

@Repository
public interface MultipleDaysCalendarSettingsRepository extends CrudRepository<MultipleDaysCalendarSettings, Integer>{
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO MultipleDaysCalendarSettings (configurationId, weekDayId)VALUES (:configurationId, :weekDayId)", 
        nativeQuery = true
        )
    Integer RegisterNewMultipleDaysCalendarSettings(@Param("configurationId") int configurationId,
                        @Param("weekDayId") int weekDayId);


    @Query(value = "Select m.weekDayId from MultipleDaysCalendarSettings m INNER JOIN calendarconfigurationusers c ON m.configurationId=c.configurationId INNER JOIN users u ON c.userId=u.userId WHERE u.username=:username", nativeQuery = true)
    List<Integer> FindWeekDaysLabourTherapist(@Param("username") String username);

    // 22-04-2024
    @Transactional
    @Modifying
    @Query(
    value = "DELETE FROM MultipleDaysCalendarSettings WHERE configurationId IN (SELECT c.configurationId FROM calendarconfigurationusers c INNER JOIN users u ON c.userId = u.userId WHERE u.username = :username)", 
    nativeQuery = true)
    Integer DeleteMultipleDays(@Param("username") String username);
}
