package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.ActivityTherapistCalendarRepository;

@Service
public class ActivityTherapistCalendarService {
    @Autowired
    ActivityTherapistCalendarRepository activityTherapistCalendarRepository;

    public java.util.List<Object[]> findRegistersOfTherapistActivitiesMethod(int userTherapistId, Date date) {
        return activityTherapistCalendarRepository.findRegistersOfTherapistActivities(userTherapistId, date);
    }

    
    public Integer addNewActivityTherapistCalendarMethod(Date date, Date startHour, Date endHour, String motive, int appointment, int userTherapistId)
    {
        return activityTherapistCalendarRepository.addNewActivityTherapistCalendar(date, startHour, endHour, motive, appointment, userTherapistId);
    }

    public Integer SearchCountActivityTherapistCalendarMethod()
    {
        return activityTherapistCalendarRepository.SearchCountActivityTherapistCalendar();
    }

    public java.util.List<Object[]> findActivitiesDatesAndHourTherapistMethod(String username)
    {
        return activityTherapistCalendarRepository.findActivitiesDatesAndHourTherapist(username);
    }

}