package com.apiLetItOut.Api.services;

import java.util.Date;

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
}
