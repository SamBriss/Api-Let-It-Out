package com.apiLetItOut.Api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.apiLetItOut.Api.repository.MultipleDaysCalendarSettingsRepository;

@Service
public class MultipleDaysCalendarSettingsService {
    
    @Autowired
    MultipleDaysCalendarSettingsRepository multipleDaysCalendarSettingsRepository;

    public Integer insertMultipleLabourWeekDaysMethod( int userId, int weekDayId)
    {
        return multipleDaysCalendarSettingsRepository.RegisterNewMultipleDaysCalendarSettings(userId, weekDayId);
    }

    public List<Integer> findWeekDaysLabourTherapistMethod(String username)
    {
        return multipleDaysCalendarSettingsRepository.FindWeekDaysLabourTherapist(username);
    }

    public Integer DeleteMultipleDaysMethod(String username)
    {
        return multipleDaysCalendarSettingsRepository.DeleteMultipleDays(username);
    }
}