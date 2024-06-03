package com.apiLetItOut.Api.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.RemindersRepository;

@Service
public class RemindersService {
    @Autowired 
    RemindersRepository remindersRepository;

    public Integer InsertIntoRemindersMethod(int userTAGId, String name, LocalTime time, int reminderType, int active)
    {
        return remindersRepository.InsertIntoReminders(userTAGId, name, time, reminderType, active);
    }

    public Integer SearchReminderIdMethod(int userTAGId, String name, LocalTime time)
    {
        return remindersRepository.SearchReminderId(userTAGId, name, time);
    }

    public Integer InsertIntoRemindersWeekdaysMethod(int reminderId, List<Integer> weekdaysId)
    {
        int result = 0;
        for (Integer weekdayId : weekdaysId) {
            result = remindersRepository.InsertIntoRemindersWeekdays(reminderId, weekdayId);
        }
        return result;
    }

    public Integer DeleteFromRemindersWeekDaysMethod(int reminderId)
    {
        return remindersRepository.DeleteFromRemindersWeekDays(reminderId);
    }

    public Integer DeleteFromRemindersMethod(int reminderId)
    {
        return remindersRepository.DeleteFromReminders(reminderId);
    }

    public List<Object[]> SelectRemindersAllDataMethod(int userTAGId)
    {
        return remindersRepository.SelectRemindersAllData(userTAGId);
    }

    public List<Integer> SelectDaysReminder(int reminderId)
    {
        return remindersRepository.SelectDaysReminder(reminderId);
    }

    public Integer SelectActiveReminderMethod(int userTAGId, String name)
    {
        return remindersRepository.SelectActiveReminder(userTAGId, name);
    }

    public Integer UpdateStatusReminderNameMethod(int userTAGId, String name, int active)
    {
        return remindersRepository.UpdateStatusReminderName(userTAGId, name, active);
    }

    public List<Object[]> SelectReminderData(int reminderId)
    {
        return remindersRepository.SelectReminderData(reminderId);
    } 

    public Integer UpdateReminderData(int reminderId, String name, int reminderTypeId, LocalTime time)
    {
        return remindersRepository.UpdateReminder(reminderId, name, reminderTypeId, time);
    }
}