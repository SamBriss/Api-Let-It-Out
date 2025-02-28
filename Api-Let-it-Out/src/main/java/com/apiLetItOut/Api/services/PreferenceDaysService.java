package com.apiLetItOut.Api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.PreferenceDaysRepository;

@Service
public class PreferenceDaysService {
    @Autowired
    PreferenceDaysRepository preferenceDaysRepository;
    public int insertPreferenceDays(int configurationId, int weekDayId, Date StartHour, Date EndHour, String label)
    {
        return this.preferenceDaysRepository.RegisterNewPreferenceDays(configurationId, weekDayId, StartHour, EndHour, label);
    }
    
    public java.util.List<Object[]> findRegistersOfTherapistExclusionTimesMethod(int configurationId) {
        return preferenceDaysRepository.findRegistersOfTherapistExclusionTimes(configurationId);
    }

    public java.util.List<Object[]> findPreferenceAppointmentsTAGMethod(String usernameTAG)
    {
        return preferenceDaysRepository.findPreferenceAppointmentsTAG(usernameTAG);
    }
    
}
