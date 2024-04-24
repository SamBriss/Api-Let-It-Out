package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.FrequencyAlertsRepository;

@Service
public class FrequencyAlertsService {
    @Autowired
    FrequencyAlertsRepository frequencyAlertsRepository;
    public Integer RegisterNewFrequencyAlertsMethod(int userTAGId, int userTherapistId){
        return frequencyAlertsRepository.RegisterNewFrequencyAlerts(userTAGId, userTherapistId);
    }
    public Integer DeleteVinculationMethod(int userTherapistId){
        return frequencyAlertsRepository.DeletefrecuencyAlerts(userTherapistId);
    }
    public List<String> SelectfrecuencyAlerts(int userTherapistId){
        return frequencyAlertsRepository.SelectfrecuencyAlerts(userTherapistId);
    }
}
