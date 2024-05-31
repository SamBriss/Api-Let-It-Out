package com.apiLetItOut.Api.config_cron;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.apiLetItOut.Api.res_controllers.AlgorithmRelaxationTechniquesApiController;
import com.apiLetItOut.Api.res_controllers.AlgorithmTriggerElementsApiController;
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

    
    @Autowired
    AlgorithmTriggerElementsApiController algorithmTriggerElementsApiController;
    
    
    @Scheduled(cron = "0 15 9 ? * THU")
    public void scheduledTaskSunday23pmAlgorithmRanking()
    {            
        // hacer el algoritmo de ranking de tecnicas de relajacion
        algorithmRelaxationTechniquesApiController.AlgorithmRankingRelaxationTechniquesAudios();

    }

    @Scheduled(cron = "0 0 4 * * *") // Se ejecuta todos los días a las 4 am
    public void executeAlgorithmOfTechniques() {
        List<String> usernames = userTAGService.SearchAllUsernameOfUsersTAGMethod();
        Map<String, Object> responseData = new HashMap<>();
        List<String> urls = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (String user : usernames) {
            responseData = algorithmOfTechniquesController.getUrlsOfTechniques(user);
            for(Map.Entry<String, Object> entry: responseData.entrySet())
            {
                String url = entry.getValue().toString();
                System.out.println(url);
                urls.add(url);
            }
            algorithmOfTechniquesController.insertDownload(urls, user, date, 0);
            urls.clear();
        }
    }
    
    @Scheduled(cron = "0 57 8 * * *")
    public void scheduledTaskSunday22AlgorithmTriggerPatterns()
    {

        int res = algorithmTriggerElementsApiController.determineMakingAlgorithm();
        System.out.println("--------------------------------------------------------------");
        if(res == 1)
        {
            System.out.println("Realizando el algoritmo de patrones desencadenantes!");
            algorithmTriggerElementsApiController.algorithmTriggerPatternsAndElements();
        }
        else if(res == 2)
        {
            // Obtener el día de la semana del día actual
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            
            // Comparar si es domingo
            if (dayOfWeek == DayOfWeek.TUESDAY) {
                System.out.println("Hoy es domingo.");
                System.out.println("Realizando el algoritmo de patrones desencadenantes!");
                algorithmTriggerElementsApiController.algorithmTriggerPatternsAndElements();                
            } else {
                System.out.println("Hoy no es domingo.");
            }
        }
        else
        {
            System.out.println("Todavía es la primera semana, no han pasado 14 dias, todavía no se hace el algoritmo de patrones desencadenantes!");
        }
        System.out.println("--------------------------------------------------------------");
    }
  
}
