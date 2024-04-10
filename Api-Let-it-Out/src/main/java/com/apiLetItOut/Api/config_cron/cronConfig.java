package com.apiLetItOut.Api.config_cron;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.apiLetItOut.Api.res_controllers.AlgorithmOfTechniques;
import com.apiLetItOut.Api.res_controllers.AppointmentCalendarApiController;
import com.apiLetItOut.Api.services.UserTAGService;

@Configuration
@EnableScheduling
public class cronConfig {
    @Autowired
    AppointmentCalendarApiController appointmentCalendarApiController;
    @Autowired
    AlgorithmOfTechniques algorithmOfTechniquesController;
    @Autowired
    UserTAGService userTAGService;

    @Scheduled(cron = "0 25 18 ? * SAT")
    public void scheduledTaskSunday23pmAlgorithmRanking()
    {            
        // hacer el algoritmo de ranking de 
        appointmentCalendarApiController.postMethodName("Araceli2002");
    }

    @Scheduled(cron = "0 0 4 * * *") // Se ejecuta todos los días a las 4 am
    public void executeAlgorithmOfTechniques() {
        List<String> usernames = userTAGService.SearchAllUsernameOfUsersTAGMethod();
        for (String user : usernames) {
            algorithmOfTechniquesController.getTechniques(user);
        }
    }
}
