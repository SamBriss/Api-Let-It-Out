package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import com.apiLetItOut.Api.repository.ActivitiesFromTherapistRepository;

@Service
public class ActivitiesFromTherapistService {
    @Autowired
    ActivitiesFromTherapistRepository activitiesFromTherapistRepository;
    
    public Integer RegisterNewActivityFromTherapistMethod( int userTAGId, int userTherapistId, String label,  String description, Date dateAsign, Date dateMax, int completed)
    {
        return activitiesFromTherapistRepository.RegisterNewActivityFromTherapist(userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed);
    }
    
    public java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAGMethod(int userTAGId)
    {
        return activitiesFromTherapistRepository.findAllActivitiesToDoFromCalendarTAG(userTAGId);
    }
    
    public java.util.List<Object[]> findAllActivitiesToDoFromCalendarTAGByDateMethod(int userTAGId, Date date)
    {
        return activitiesFromTherapistRepository.findAllActivitiesToDoFromCalendarTAGByDate(userTAGId, date);
    }
}
