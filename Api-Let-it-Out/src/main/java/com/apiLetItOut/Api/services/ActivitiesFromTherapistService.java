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
}
