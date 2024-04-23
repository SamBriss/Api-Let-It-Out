package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.CalendarConfigurationRepository;

@Service
public class CalendarConfigurationUsersService {
    
    @Autowired
    CalendarConfigurationRepository calendarConfigurationRepository;

    public Integer RegisterNewCalendarConfigurationMethod( int userId, Date startWorkDay, Date endWorkDay)
    {
        return calendarConfigurationRepository.RegisterNewCalendarConfiguration(userId, startWorkDay, endWorkDay);
    }
    public Integer SearchConfigurationIdByUserIdMethod( int userId)
    {
        return calendarConfigurationRepository.SearchConfigurationIdByUserId(userId);
    }
    public Object SearchStartHourJourneyMethod(String usernameTherapist)
    {
        return calendarConfigurationRepository.SearchStartHourJourney(usernameTherapist);
    }
    
    public Object SearchEndHourJourneyMethod(String usernameTherapist)
    {
        return calendarConfigurationRepository.SearchEndHourJourney(usernameTherapist);
    }
    // 20-04-2024
    public List<Object[]> getPreferenceDaysUserMethod(String username)
    {
        return calendarConfigurationRepository.getPreferenceDaysUser(username);
    }
    public int findConfigurationIdByUsernameMethod(String username)
    {
        return calendarConfigurationRepository.findConfigurationIdByUsername(username);
    }
    public int updatePreferenceDaysMethod(int preferenceDayId, int weekDayId, Date StartHour, Date EndHour, String label)
    {
        return calendarConfigurationRepository.UpdatePreferenceDays(preferenceDayId,  StartHour, EndHour,weekDayId, label);
    }
    public int UpdateWorkTimesMethod(int configurationId, Date startHour, Date endHour)
    {
        return calendarConfigurationRepository.UpdateWorkTimes(configurationId, startHour, endHour);
    }
}
