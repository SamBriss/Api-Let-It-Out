package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

import com.apiLetItOut.Api.repository.ActivitiesFromTherapistRepository;

@Service
public class ActivitiesFromTherapistService {
    @Autowired
    ActivitiesFromTherapistRepository activitiesFromTherapistRepository;
    
    public Integer RegisterNewActivityFromTherapistMethod( int userTAGId, int userTherapistId, String label,  String description, Date dateAsign, Date dateMax, int completed)
    {
        return activitiesFromTherapistRepository.RegisterNewActivityFromTherapist(userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed);
    }
    public Integer CountRequestQuantityActivitiesMethod( int userTAGId)
    {
        return activitiesFromTherapistRepository.CountRequestQuantityActivities(userTAGId);
    }
    public List<Integer> SelectActivityIdMethod( int userTAGId)
    {
        return activitiesFromTherapistRepository.SelectActiivityId(userTAGId);
    }
    public List<Integer> SelectTherapistIdMethod( int userTAGId)
    {
        return activitiesFromTherapistRepository.SelectTherapistId(userTAGId);
    }
    public Integer UpdateCommentsTherapistMethod(int activityTId, String comments)
    {
        return activitiesFromTherapistRepository.UpdateCommentsTherapist(activityTId, comments);
    }
}
