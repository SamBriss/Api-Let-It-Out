package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.FrecuencyGraphicsRepository;

@Service
public class FrecuencyGraphicsService {
    @Autowired
    FrecuencyGraphicsRepository frecuencyGraphicsRepository;
    public List<Object[]> SelectWeekMethod(int userTAGId)
    {
        return frecuencyGraphicsRepository.SelectWeek(userTAGId);
    }
    public List<Object[]> SelectMonthMethod(int userTAGId)
    {
        return frecuencyGraphicsRepository.SelectMonth(userTAGId);
    }
    public List<Object[]> SelectSixMonthsMethod(int userTAGId)
    {
        return frecuencyGraphicsRepository.SelectSixMonths(userTAGId);
    }
    public List<Object[]> SelectYearMethod(int userTAGId)
    {
        return frecuencyGraphicsRepository.SelectYear(userTAGId);
    }
    public String increaseOrDecreaseMethod(int userTAGId){
        return frecuencyGraphicsRepository.increaseOrDecrease(userTAGId);
    }
}
