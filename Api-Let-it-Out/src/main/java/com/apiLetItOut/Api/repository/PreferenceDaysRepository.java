package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.PreferenceDays;

import jakarta.transaction.Transactional;

@Repository
public interface PreferenceDaysRepository extends CrudRepository<PreferenceDays, Integer>{
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO preference_days (configurationId, weekDayId, StartHour, EndHour, label)VALUES (:configurationId, :weekDayId, :StartHour, :EndHour, :label)", 
        nativeQuery = true
        )
    Integer RegisterNewPreferenceDays(@Param("configurationId") int configurationId,
                        @Param("weekDayId") int weekDayId,
                        @Param("StartHour") Date StartHour,
                        @Param("EndHour") Date EndHour,
                        @Param("label") String label);
   
                        
    @Query(value= "Select u.startHour, u.endHour, u.date FROM preference_days u WHERE u.configurationId=:configurationId", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfTherapistExclusionTimes(@Param("configurationId") int configurationId);

}
