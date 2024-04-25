package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AlgorithmRelaxationTechniquesService;
import com.apiLetItOut.Api.services.PreferencesTAGUserService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;






@RestController
@RequestMapping("api")
public class AlgorithmRelaxationTechniquesApiController {

    @Autowired
    AlgorithmRelaxationTechniquesService algorithmRelaxationTechniquesService;

    @Autowired
    UserService userService;

    @Autowired
    PreferencesTAGUserService preferencesTAGUserService;

    @Autowired
    UserTAGService userTAGService;


    @PostMapping("techniquesRanking/AlgorithmBDSave")
    public ResponseEntity AlgorithmRankingRelaxationTechniquesAudios() {

        int countRanking = algorithmRelaxationTechniquesService.countRankingExitenceRegistersMethod();
        System.out.println("countRanking: "+countRanking);
        int categoryIdRanking;
        int categoryIdActual;
        if(countRanking == 0)
        {
            categoryIdRanking = 7;
        } 
        else
        {
            categoryIdRanking = algorithmRelaxationTechniquesService.getCategoryIdFromLastRankingIdMethod();
            System.out.println("categoryIdRanking:   "+categoryIdRanking);
        }
        LocalDate currentDate = LocalDate.now();

        // Obtén la última fecha de ejecución
        Object lastExecutionObj = algorithmRelaxationTechniquesService.selectLastExecutionDate();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long daysDifference = 0;
            if(lastExecutionObj!=null)
            {
            Date lastExecutionDate = format.parse(lastExecutionObj.toString());
            System.out.println(lastExecutionDate);
            
            LocalDate lastExecutionLocalDate = lastExecutionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            // Calcula la diferencia de días entre la fecha actual y la última fecha de ejecución
            daysDifference = ChronoUnit.DAYS.between(lastExecutionLocalDate, currentDate);
            System.out.println("days diference:  "+daysDifference);
            }
            if(daysDifference >= 7 || daysDifference == 0)
            {
                int totalUsesOfTechniques = algorithmRelaxationTechniquesService.getCountOfFeedbacksMethod();
                int result = -1;
                int countPreferenceAudios = 0;
                Object[] obj = new Object[2];
                if(totalUsesOfTechniques > 0)
                {
                    List<Integer> usersTAGIdList = algorithmRelaxationTechniquesService.GetUserTAGIdFromCountRankingMethod();
                    for(int u : usersTAGIdList)
                    {
                        int res = algorithmRelaxationTechniquesService.UpdateCountRankingMethod(u, 0);
                        System.out.println("res de update: "+res);
                    }
                    switch (categoryIdRanking) {
                        case 5:
                        // semana 2 primera preferencia sensorial 1 
                            categoryIdActual = 2;
                            List<Object[]> list_visualizacion = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Visual");
                            List<Object[]> list_olfato = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Olfato");
                            List<Object[]> list_gusto = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Gusto");
                            List<Object[]> list_oido  = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Oido");
                            List<Object[]> list_tacto = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Tacto");
                            
                            result = 0;
                            countPreferenceAudios = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Visual");
                            result += processToEachPreferenceOfEveryCategory(list_visualizacion, countPreferenceAudios, categoryIdActual, "Visual");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Olfato");
                            result += processToEachPreferenceOfEveryCategory(list_olfato, countPreferenceAudios, categoryIdActual, "Olfato");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Gusto");
                            result += processToEachPreferenceOfEveryCategory(list_gusto, countPreferenceAudios, categoryIdActual, "Gusto");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Oido");
                            result += processToEachPreferenceOfEveryCategory(list_oido, countPreferenceAudios, categoryIdActual, "Oido");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Tacto");
                            result += processToEachPreferenceOfEveryCategory(list_tacto, countPreferenceAudios, categoryIdActual, "Tacto");


                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        case 2:
                        // semana 3 segunda preferencia sensorial
                            categoryIdActual = 6;
                            List<Object[]> list_visualizacion2 = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Visual");
                            List<Object[]> list_olfato2 = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Olfato");
                            List<Object[]> list_gusto2 = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Gusto");
                            List<Object[]> list_oido2  = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Oido");
                            List<Object[]> list_tacto2 = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndSensorialPreferenceMethod("Tacto");
                            
                            result = 0; 
                            countPreferenceAudios = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Visual");
                            result += processToEachPreferenceOfEveryCategory(list_visualizacion2, countPreferenceAudios, categoryIdActual, "Visual");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Olfato");
                            result += processToEachPreferenceOfEveryCategory(list_olfato2, countPreferenceAudios, categoryIdActual, "Olfato");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Gusto");
                            result += processToEachPreferenceOfEveryCategory(list_gusto2, countPreferenceAudios, categoryIdActual, "Gusto");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Oido");
                            result += processToEachPreferenceOfEveryCategory(list_oido2, countPreferenceAudios, categoryIdActual, "Oido");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceSensorialMethod("Tacto");
                            result += processToEachPreferenceOfEveryCategory(list_tacto2, countPreferenceAudios, categoryIdActual, "Tacto");

                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        case 6:
                        // semana 4 preferencia auditivas 
                            categoryIdActual = 1;
                            List<Object[]> list_GuiaVoz = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndAuditivePreferenceMethod("GuiaVoz");
                            List<Object[]> list_Musica = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndAuditivePreferenceMethod("Musica");
                            List<Object[]> list_SonidoAmbiente = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndAuditivePreferenceMethod("Sonido Ambiente");
                            
                            result = 0;
                            countPreferenceAudios = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAuditiveMethod("GuiaVoz");
                            result += processToEachPreferenceOfEveryCategory(list_GuiaVoz, countPreferenceAudios, categoryIdActual, "GuiaVoz");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAuditiveMethod("Musica");
                            result += processToEachPreferenceOfEveryCategory(list_Musica, countPreferenceAudios, categoryIdActual, "Musica");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAuditiveMethod("Sonido Ambiente"); 
                            result += processToEachPreferenceOfEveryCategory(list_SonidoAmbiente, countPreferenceAudios, categoryIdActual, "Sonido Ambiente");

                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        case 1:
                        // semana 5 preferencia duracion 
                            categoryIdActual = 4; 
                            List<Object[]> list_cortas = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndDurationMethod(1,300);
                            List<Object[]> list_medianas = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndDurationMethod(301, 900);
                            List<Object[]> list_largas = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndDurationMethod(901, 15000);
                            
                            result = 0;
                            countPreferenceAudios = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceDurationMethod(1,300);
                            result += processToEachPreferenceOfEveryCategory(list_cortas, countPreferenceAudios, categoryIdActual, "Cortas");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceDurationMethod(301,900);
                            result += processToEachPreferenceOfEveryCategory(list_medianas, countPreferenceAudios, categoryIdActual, "Medias");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceDurationMethod(901,15000);
                            result += processToEachPreferenceOfEveryCategory(list_largas, countPreferenceAudios, categoryIdActual, "Largas");
                            
                            
                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        case 4:
                        // semana 6 edad usuario tagbreak;
                            categoryIdActual = 7;
                            // ver si lo hago por edad de la tecnica o entonces por edad de todos los usuarios
                            List<Object[]>list_13_17_years = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsAudioIdByAgeUserMethod(13, 17);
                            List<Object[]> list_18_30_years = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsAudioIdByAgeUserMethod(18, 30);
                            List<Object[]> list_31_45_years =algorithmRelaxationTechniquesService.getAverageScoreRepetitionsAudioIdByAgeUserMethod(31, 45);
                            List<Object[]> list_46_60_years = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsAudioIdByAgeUserMethod(46, 60);
                            List<Object[]> list_60_More_years = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsAudioIdByAgeUserMethod(61, 130);

                            result = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAgeMethod(13, 17);
                            result += processToEachPreferenceOfEveryCategory(list_13_17_years, countPreferenceAudios, categoryIdActual, "13-17");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAgeMethod(18, 30);
                            result += processToEachPreferenceOfEveryCategory(list_18_30_years, countPreferenceAudios, categoryIdActual, "18-30");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAgeMethod(31, 45);
                            result += processToEachPreferenceOfEveryCategory(list_31_45_years, countPreferenceAudios, categoryIdActual, "31-45");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAgeMethod(46, 60);
                            result += processToEachPreferenceOfEveryCategory(list_46_60_years, countPreferenceAudios, categoryIdActual, "46-60");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAgeMethod(60, 130);
                            result += processToEachPreferenceOfEveryCategory(list_60_More_years, countPreferenceAudios, categoryIdActual, "60-Mas");

                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        case 7:
                        // semana 1 preferencia estilo de vida
                            categoryIdActual = 5;
                            List<Object[]> list_Pasiva = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndLifestylePreferenceMethod("p");
                            List<Object[]> list_Activa = algorithmRelaxationTechniquesService.getAverageScoreRepetitionsByAudioIdAndLifestylePreferenceMethod("a");
                            
                            result = 0;
                            countPreferenceAudios = 0;
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAuditoryMethod("p");
                            result += processToEachPreferenceOfEveryCategory(list_Pasiva, countPreferenceAudios, categoryIdActual,"p");
                            countPreferenceAudios = algorithmRelaxationTechniquesService.getCountOfFeedbacksByPreferenceAuditoryMethod("a");
                            result += processToEachPreferenceOfEveryCategory(list_Activa, countPreferenceAudios, categoryIdActual, "a");
                            
                            obj[0] = categoryIdActual;
                            obj[1] = result;

                        return ResponseEntity.ok().body(obj);

                        
                        default:
                            break;
                    }
                }
                
                return ResponseEntity.ok("FoundNoTechniquesFeedback");
            }
            else
            {
                return ResponseEntity.ok("RankingOfTheWeekAlreadyMade");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.ok("WrongLastDate");
        }
    }
 
    private int processToEachPreferenceOfEveryCategory(List<Object[]> list_preference, int totalUsesOfTechniques, int categoryIdActual, String preferenceName)
    {
        List<Object[]> list_score_count_preference = new ArrayList<>();
        int countPreference = -1;
        int i = 0;
        double tempCalificacionFinal = -1;
        int result = 0;
        int audioIdChoosen = 0;
        int countObjChoosen = 0;
        
        LocalDate fechaActual = LocalDate.now();
        for(Object[] obj : list_preference)
        {
            int audioId = Integer.parseInt(obj[0].toString());
            System.out.println("audioId: "+audioId);
            
            
            int countObj = algorithmRelaxationTechniquesService.getCountOfAudioIdInFeedbacksMethod(audioId);
            System.out.println("count audio: "+countObj);
            // .substring(0, obj[2].toString().length()-4) 
            System.out.println("score average users: "+obj[2].toString());
            double score = Double.parseDouble(String.valueOf(obj[2].toString()));
            double calif60 = (score * 60)/5;
            System.out.println("calif60: "+calif60);
           
            double calif40 = countObj*60/totalUsesOfTechniques;
            System.out.println("calif40: "+calif40);
            double calificacionFinal = calif60 + calif40;
            System.out.println("calificacionfinal: "+calificacionFinal);
           
            Object[] newObj = new Object[5];
            newObj[0] = obj[0];
            newObj[1] = obj[1];
            newObj[2] = obj[2];
            newObj[3] = countObj;
            newObj[4] = calificacionFinal;
            list_score_count_preference.add(obj);
            if(tempCalificacionFinal < calificacionFinal)
            {
                tempCalificacionFinal = calificacionFinal;
                countPreference = i;
                audioIdChoosen = audioId;
                countObjChoosen = countObj;
            }
            i++;
        }
        System.out.println("countpreference: "+countPreference);
        if (countPreference != -1) {
            // add to ranking
            
        
            result = algorithmRelaxationTechniquesService.addNewRankingPreferenceCategoryMethod(
                    audioIdChoosen, // int
                    tempCalificacionFinal, // double
                    countObjChoosen, // int
                    Double.parseDouble(list_score_count_preference.get(countPreference)[2].toString()), // double
                    Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()), // date
                    categoryIdActual, // int
                    preferenceName // string
            );
        } else {
            // add one that found no scores and calculates nothing
            result = algorithmRelaxationTechniquesService.addNewRankingPreferenceCategorywithNullMethod(
                    Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    categoryIdActual,
                    preferenceName
            );
        }
    
    
        return result;

    }
     
    @PostMapping("techniquesRanking/GetTechniqueForUser")
    public ResponseEntity postMethodName(@RequestParam("username") String username) {
        
        
        int countRanking = algorithmRelaxationTechniquesService.countRankingExitenceRegistersMethod();
        System.out.println("countRanking: "+countRanking);
        int categoryIdRanking;
        int categoryIdActual;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date dateExecution;
        String categoryName = "0";

        if(countRanking > 0)
        {
            try{
                categoryIdRanking = algorithmRelaxationTechniquesService.getCategoryIdFromLastRankingIdMethod();
                dateExecution = algorithmRelaxationTechniquesService.getDateFromLastRankingIdMethod();
                
                System.out.println("ctaegory id of the week: "+categoryIdRanking);
                System.out.println("date execution: "+dateExecution);
                int userId = userService.SearchUserTAGMethod(username);
                int userTAGId = userTAGService.FindUserTAGMethod(userId);
                String preference = "0";
                HashMap scores = new HashMap<>();
                List<Map.Entry<Integer, String>> listScores = new ArrayList<>(scores.entrySet());
                switch(categoryIdRanking)
                {
                    // edad
                    case 7:
                        int age = userService.SearchUserAgeMethod(userId);
                        if(age>=13 && age <=17)
                        {
                            preference = "13-17";
                        }
                        else if(age > 17 && age <=30)
                        {
                            preference = "18-30";
                        }
                        else if(age > 30 && age <= 45)
                        {
                            preference = "31-45";
                        }
                        else if(age > 45 && age <= 60)
                        {
                            preference = "46-60";
                        }
                        else if(age > 60)
                        {
                            preference = "60-Mas";
                        }
                    break;
                        
                    // preferencia sensorial 1
                    case 2:
                    System.out.println("entra en category 2");
                    System.out.println("usertagId:  "+userTAGId);
                    System.out.println(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Tacto")+ "tacto");
                    System.out.println(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Visual") + "Visual");
                    System.out.println(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Olfato")+ "Olfato");
                    System.out.println(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Gusto")+ "Gusto");
                    System.out.println(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Oido")+ "Oido");
                   
                    scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Tacto"), "Tacto");
                    scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Visual"), "Visual");
                    scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Olfato"), "Olfato");
                    scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Gusto"), "Gusto");
                    scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Oido"), "Oido");

                    listScores = new ArrayList<>(scores.entrySet());
                    Collections.sort(listScores, new Comparator<Map.Entry<Integer, String>>() {

                        @Override
                        public int compare(Entry<Integer, String> arg0, Entry<Integer, String> arg1) {
                            // TODO Auto-generated method stub
                            if(arg0 != null)
                            {
                                return arg0.getKey().compareTo(arg1.getKey());
                            }
                            return 0;
                        }
                        
                    });

                    Map.Entry<Integer, String> valueMaxPreferenceSensorial = listScores.get(listScores.size() -1);
                    preference = valueMaxPreferenceSensorial.getValue();
                    System.out.println("preference: "+preference);
                    
                    break;
                    // segunda mayor preferencia sensorial
                    case 6: 
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Tacto"), "Tacto");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Visual"), "Visual");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Olfato"), "Olfato");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Gusto"), "Gusto");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Oido"), "Oido");


                        listScores = new ArrayList<>(scores.entrySet());
                        Collections.sort(listScores, new Comparator<Map.Entry<Integer, String>>() {

                            @Override
                            public int compare(Entry<Integer, String> arg0, Entry<Integer, String> arg1) {
                                // TODO Auto-generated method stub
                                return arg0.getKey().compareTo(arg1.getKey());
                            }
                            
                        });

                        Map.Entry<Integer, String> secondMaxValue = listScores.get(listScores.size() -2);
                        preference = secondMaxValue.getValue();

                    break;
                    // preferencias estilo de vida
                    case 5:
                        int scoreP = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Pasivo");
                        int scoreA = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Activo");
                        preference = "p";
                        if(scoreA> scoreP)
                        {
                            preference = "a";
                        }
                    break;
                    // preferencia duracion
                    case 4:
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Cortas"), "Cortas");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Medias"), "Medias");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Largas"), "Largas");

                        listScores = new ArrayList<>(scores.entrySet());

                        Collections.sort(listScores, new Comparator<Map.Entry<Integer, String>>() {

                            @Override
                            public int compare(Entry<Integer, String> arg0, Entry<Integer, String> arg1) {
                                // TODO Auto-generated method stub
                                return arg0.getKey().compareTo(arg1.getKey());
                            }
                            
                        });

                        Map.Entry<Integer, String> valueDuracion = listScores.get(listScores.size() -1);
                        preference = valueDuracion.getValue();
                        break;
                    // preferencia auditiva
                    case 1:
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Sonido Ambiente"), "Sonido Ambiente");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "GuiaVoz"), "GuiaVoz");
                        scores.put(preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, "Musica"), "Musica");

                        listScores = new ArrayList<>(scores.entrySet());

                        Collections.sort(listScores, new Comparator<Map.Entry<Integer, String>>() {

                            @Override
                            public int compare(Entry<Integer, String> arg0, Entry<Integer, String> arg1) {
                                // TODO Auto-generated method stub
                                return arg0.getKey().compareTo(arg1.getKey());
                            }
                            
                        });

                        Map.Entry<Integer, String> valueAuditiva = listScores.get(listScores.size() -1);
                        preference = valueAuditiva.getValue();
                    break;

                }
                if(preference != "0")
                {
                    System.out.println("preference choosen: "+preference);
                    
                    Object resultToReturn = algorithmRelaxationTechniquesService.getTechniqueOfTheWeekForEachUserMethod(preference, dateExecution);
                    if(resultToReturn!= null)
                    {
                        return ResponseEntity.ok().body(resultToReturn);
                    }
                    return ResponseEntity.ok().body("nadie ha usado su preferencia");
                }
                return ResponseEntity.ok().body("no se encontró su preferencia");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        } 
        
        return ResponseEntity.ok().body("not found");
    }
    
    @PostMapping("countByUser/getCountForTAGUser")
    public ResponseEntity getCountForTAGUserRanking(@RequestParam("username") String username) {
        
        int count = algorithmRelaxationTechniquesService.getCountByUserTAGMethod(username);
        if(count>0)
        {
            return ResponseEntity.ok().body("1");
        }
        return ResponseEntity.ok().body(count);
    }

    @PostMapping("countByUser/changeCountForTAGUser")
    public ResponseEntity UpdateCountForTAGUserRanking(@RequestParam("username") String username) {
        
        int userId = userService.SearchUserTAGMethod(username);
        int count = algorithmRelaxationTechniquesService.getCountByUserTAGMethod(username);
        count ++;
        if(userId>0)
        {
            int userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId>0)
            {
                int result = algorithmRelaxationTechniquesService.UpdateCountRankingMethod(userTAGId, count);
                if(result > 0)
                {
                    return ResponseEntity.ok().body("1");
                }
            }
        }
        return ResponseEntity.ok().body("0");
    }
}
