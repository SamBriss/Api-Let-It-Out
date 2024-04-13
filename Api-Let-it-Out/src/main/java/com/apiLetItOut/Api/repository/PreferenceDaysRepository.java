package com.apiLetItOut.Api.repository;

import java.util.Date;

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
   
// esta tengo q revisarla despues creo que está mal                        
    @Query(value= "Select u.startHour, u.endHour, u.date FROM preference_days u WHERE u.configurationId=:configurationId", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfTherapistExclusionTimes(@Param("configurationId") int configurationId);

    @Query(value = "Select p.StartHour, p.EndHour, p.weekDayId from preference_days p INNER JOIN calendarconfigurationusers c ON p.configurationId=c.configurationId INNER JOIN users u ON c.userId=u.userId WHERE u.username=:usernameTAG", nativeQuery = true)
    java.util.List<Object[]> findPreferenceAppointmentsTAG(@Param("usernameTAG") String usernameTAG);
}
