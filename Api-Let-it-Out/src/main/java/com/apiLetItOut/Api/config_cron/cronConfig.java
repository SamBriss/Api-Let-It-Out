package com.apiLetItOut.Api.config_cron;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.apiLetItOut.Api.res_controllers.AlgorithmRelaxationTechniquesApiController;
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

    @Autowired
    AlgorithmRelaxationTechniquesApiController algorithmRelaxationTechniquesApiController;
    
    @Scheduled(cron = "0 0 23 ? * SUN")
    public void scheduledTaskSunday23pmAlgorithmRanking()
    {            
        // hacer el algoritmo de ranking de tecnicas de relajacion
        algorithmRelaxationTechniquesApiController.AlgorithmRankingRelaxationTechniquesAudios();

    }

    /*@Scheduled(cron = "0 0 4 * * *") // Se ejecuta todos los días a las 4 am
    public void executeAlgorithmOfTechniques() {
        List<String> usernames = userTAGService.SearchAllUsernameOfUsersTAGMethod();
        for (String user : usernames) {
            algorithmOfTechniquesController.getTechniques(user);
        }
    }*/
}
