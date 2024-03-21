package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.MultipleDaysCalendarSettings;

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

}
