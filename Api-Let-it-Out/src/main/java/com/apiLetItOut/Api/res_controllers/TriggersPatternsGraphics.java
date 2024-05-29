package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import com.apiLetItOut.Api.services.AlgorithmTriggerElementsService;
import com.apiLetItOut.Api.services.AttackRegistersService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api/graphicTriggerPatterns")
public class TriggersPatternsGraphics {

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    AttackRegistersService attackRegistersService;

    @Autowired
    AlgorithmTriggerElementsService algorithmTriggerElementsService;

    @PostMapping("/getData")
    public ResponseEntity<Map<String, Object>> getData(String user)
    {
        Map<String, Object> responseData = new HashMap<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if(userTAGId == null)
        {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if(userTAGId!=null)
        {
            
            List<Integer> attacksId = attackRegistersService.SearchRegistersAttackCompletedMethod(userTAGId);
            if(attacksId!=null && !attacksId.isEmpty())
            {
                List<Integer> patternsId = algorithmTriggerElementsService.searchExistenceOfPatterns(userTAGId);
                if(!patternsId.isEmpty() && patternsId!=null )
                {

                    List<Integer> wordsOfTriggersLocations = algorithmTriggerElementsService.SearchWordsPatternsMethod(userTAGId, 1);
                    List<Integer> wordsOfTriggersDayMoments = algorithmTriggerElementsService.SearchWordsPatternsMethod(userTAGId, 2);
                    List<Integer> wordsOfTriggersActivities = algorithmTriggerElementsService.SearchWordsPatternsMethod(userTAGId, 3);
                    List<Date> datesPatterns = algorithmTriggerElementsService.SearchDatesOfPatternsMethod();
                    Date dateRegister = userTAGService.SelectRegisterDateMethod(userTAGId);
                    List<Date> allDates = new ArrayList<>(datesPatterns);

                    // Añadir la fecha de registro si es posterior a alguna fecha en datesPatterns
                    boolean added = false;
                    for (Date date : datesPatterns) {
                        if (date.after(dateRegister) && !added) {
                            allDates.add(dateRegister);
                            added = true;
                        }
                    }

                    // Si la fecha de registro es después de todas las fechas en datesPatterns, añadirla al final
                    if (!added) {
                        allDates.add(dateRegister);
                    }

                    // Ordenar todas las fechas en orden ascendente
                    Collections.sort(allDates);

                    // Ahora allDates contiene todas las fechas ordenadas

                    
                    int i=0;
                    for (Integer wordId : wordsOfTriggersLocations) {
                        //System.out.println(wordId.toString());
                        //Integer countOfword = 
                        String locationPoints = "";
                        List<Object[]> points = algorithmTriggerElementsService.SearchPointGraphicByWordIdMethod(userTAGId, wordId);
                        if(!points.isEmpty())
                        {
                            for(Object[] point: points)
                            {
                                locationPoints += point[0] + ":" + point[1] + ":" + point[2] +";";
                            }
                            responseData.put("locations" + i, locationPoints);
                            System.out.println(locationPoints);
                        }
                        i++;
                    }
                    i=0;
                    for (Integer wordId : wordsOfTriggersDayMoments) {
                        String momentsPoints = "";
                        List<Object[]> points = algorithmTriggerElementsService.SearchPointGraphicByWordIdMethod(userTAGId, wordId);
                        if(!points.isEmpty())
                        {
                            for(Object[] point: points)
                            {
                                momentsPoints += point[0] + ":" +point[1] + ":" + point[2] + ";";
                            }
                            responseData.put("moments"+i, momentsPoints);
                        }
                        i++;
                    }
                    i=0;
                    for (Integer wordId : wordsOfTriggersActivities) {
                        String activitiesPoints = "";
                        List<Object[]> points = algorithmTriggerElementsService.SearchPointGraphicByWordIdMethod(userTAGId, wordId);
                        if(!points.isEmpty())
                        {
                            for(Object[] point: points)
                            {
                                activitiesPoints += point[0] + ":" +point[1] + ":" + point[2] + ";";
                            }
                            responseData.put("activities"+i, activitiesPoints);
                        }
                        i++;
                    }
                    responseData.put("dates", allDates);
                }
            }
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
        
    }
}
