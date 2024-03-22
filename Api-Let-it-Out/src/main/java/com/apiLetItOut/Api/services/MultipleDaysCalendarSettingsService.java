package com.apiLetItOut.Api.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.CalendarConfigurationRepository;
import com.apiLetItOut.Api.repository.MultipleDaysCalendarSettingsRepository;

@Service
public class MultipleDaysCalendarSettingsService {
    
    @Autowired
    MultipleDaysCalendarSettingsRepository multipleDaysCalendarSettingsRepository;

    public Integer insertMultipleLabourWeekDaysMethod( int userId, int weekDayId)
    {
        return multipleDaysCalendarSettingsRepository.RegisterNewMultipleDaysCalendarSettings(userId, weekDayId);
    }
}