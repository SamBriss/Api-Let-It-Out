package com.apiLetItOut.Api.config_cron;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.apiLetItOut.Api.res_controllers.AppointmentCalendarApiController;


@Configuration
@EnableScheduling
public class cronConfig {
    @Autowired
    AppointmentCalendarApiController appointmentCalendarApiController;

    @Scheduled(cron = "0 25 18 ? * SAT")
    public void scheduledTaskSunday23pmAlgorithmRanking()
    {            
        // hacer el algoritmo de ranking de 
        appointmentCalendarApiController.postMethodName("Araceli2002");

    }
}
