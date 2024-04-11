package com.apiLetItOut.Api.services;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.CalendarTAGActivityRepository;

@Service
public class CalendarTAGActivityService {
    @Autowired
    CalendarTAGActivityRepository calendarTAGActivityRepository;

    public java.util.List<Object[]> findRegistersOfUserTagActivitiesMethod(int userTAGId, Date date) {
        return calendarTAGActivityRepository.findRegistersOfUserTagActivities(userTAGId, date);
    }

    public java.util.List<Object[]> findAllActivitiesFromCalendarTAGMethod(int userTAGId)
    {
        return calendarTAGActivityRepository.findAllActivitiesFromCalendarTAG(userTAGId);
    }
  
    public java.util.List<Object[]> findAllActivitiesFromCalendarTAGByDateMethod(int userTAGId, Date date)
    {
        return calendarTAGActivityRepository.findAllActivitiesFromCalendarTAGByDate(userTAGId, date);
    }

    public Integer addNewActivityUserTagCalendarMethod(int userTAGId, String label, String location, String direction, Date date, Date startHour, Date endHour, Date dateRegister, String comments, int reminders)
    {
        return calendarTAGActivityRepository.addNewActivityUserTagCalendar(userTAGId, label, location, direction, date, startHour, endHour, dateRegister, comments, reminders);
    }

    public Integer SearchCountActivityUserTagCalendarMethod()
    {
        return calendarTAGActivityRepository.SearchCountActivityUserTagCalendar();
    }

}