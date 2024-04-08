package com.apiLetItOut.Api.res_controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ListenedAudiosFeedbackService;
import com.apiLetItOut.Api.services.PreferencesTAGUserService;
import com.apiLetItOut.Api.services.RelaxationTechniquesService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/algorithmTechniques/")
public class AlgorithmOfTechniques {

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @Autowired
    RelaxationTechniquesService relaxationTechniquesService;

    @Autowired
    ListenedAudiosFeedbackService listenedAudiosFeedbackService;

    @Autowired
    PreferencesTAGUserService preferencesTAGUserService;

    @PostMapping("searchUrls")
    public void getTechniques(@RequestParam("user") String user) 
    {
        int userId = getUserID(user);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        int age = userService.SearchUserAgeMethod(userId);
        char ageRange = getAgeRange(age);
        int levelTAGId = userTAGService.SearchLevelTAGMethod(userTAGId);
        String genderStr  = userService.SearchUserGenderMethod(userId);
        char gender = genderStr.charAt(0);
        Map<Integer, Double> scoresAndId = new HashMap<>();
        List<Integer> firstFilterAudios = relaxationTechniquesService.allAudiosMinor5MinMethod();
        for (Integer audioId : firstFilterAudios) {
            double calificationOfAudio = getCalification(userTAGId, audioId, levelTAGId, age);
            scoresAndId.put(audioId, calificationOfAudio);
        }
        // Ordenar el HashMap por calificación
        scoresAndId = sortByValue(scoresAndId);
        if (scoresAndId.size() > 14) // Filtro por edades
        { 
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                if (age > 44) { // Busca audios que sean activos para personas mayores
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToLifeStyleAndIdMethod(audioId, 'a');
                } else if (age < 14) { // Busca audios especiales para niños
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToAgeAndIdMethod(audioId, ageRange);
                }
                if (filteredAudioId != null) {
                    keysToRemove.add(filteredAudioId);
                }
            }
            // Eliminar las claves marcadas para eliminación
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if(scoresAndId.size()>14)//Busca tecnicas que tengan una calificacion menor a 3
        {
            
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = listenedAudiosFeedbackService.SearchIdByScore(userTAGId, audioId);
                if (filteredAudioId != null) {
                    keysToRemove.add(filteredAudioId);
                }
            }

            // Eliminar las claves marcadas para eliminación
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        Map<Integer, Double> audiosPassives = new HashMap<>();
        String lifeStylePrefered = preferencesTAGUserService.SearchLifeStylePreference(userTAGId, 5);
        lifeStylePrefered = lifeStylePrefered.toLowerCase();
        char auditory = lifeStylePrefered.charAt(0);
        if(scoresAndId.size()>14) //Por estilo de vida
        {
            List<Integer> audiosPassivesId = new ArrayList<>();
            int i=0;
            if(lifeStylePrefered.equals("Activo"))
            {
                for (Integer audioId : scoresAndId.keySet()) {
                    if(i<6)
                    {
                        Integer audioIdPassive = relaxationTechniquesService.SearchAudioByIdAndDifferentAuditoryMethod(audioId, 'a');
                        if(audioIdPassive!=null)
                        {
                            audiosPassivesId.add(audioIdPassive);
                            i++; 
                        }
                        
                    }
                }
                for (Integer audioId : audiosPassivesId) {
                    double calificationOfAudio = getCalification(userTAGId, audioId, levelTAGId, age);
                    audiosPassives.put(audioId, calificationOfAudio);
                }
                audiosPassives = sortByValue(audiosPassives);
                //scoresAndId.putAll(audiosPassives);
            }
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentAuditoryMethod(audioId, auditory);
                if(filteredAudioId!=null)
                {
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if(scoresAndId.size()>14) // Filtro por hora del día
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToHourAndId(audioId, 't');
                if(filteredAudioId!=null)
                {
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 14) // Filtro de preferencias auditivas con 0
        {
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> preferencesAuditives0 = preferencesTAGUserService.SearchPreferenceAuditive0ScoreMethod(userTAGId, 1);
            for (String preference : preferencesAuditives0) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService.SearchAudioByPreferenceAuditive0AndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        keysToRemove.add(audioId);
                    }
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 14) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 14) // Filtro por preferencias sensoriales menos gustadas
        { 
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> lastPreferencesSensoriales = preferencesTAGUserService.SearchLastPreferencesSensoriales(userTAGId, 2);
            for (String preference : lastPreferencesSensoriales) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService.SearchAudioByPreferenceSensorialAndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        keysToRemove.add(audioId);
                    }
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 14) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if(scoresAndId.size()>14) // Filtro de calificaciones de usuarios similares
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Double scoresSimilarsUsers = CalificationSimilarsUsers(age, levelTAGId, audioId);
                if (scoresSimilarsUsers != null && scoresSimilarsUsers<2.0 && scoresSimilarsUsers>0.0) {
                    keysToRemove.add(audioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 14) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if(scoresAndId.size()>14) // Filtro de nivel de ansieadad
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentLevelTAGMethod(audioId, levelTAGId);
                if(filteredAudioId!=null)
                {
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
        }
        if(scoresAndId.size()>14)
        {   
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentGenderMethod(audioId, gender, 't');
                if(filteredAudioId!=null)
                {
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if(scoresAndId.size()>14)
                {
                    scoresAndId.remove(key);
                }else{
                    break;
                }
            }
        }
        scoresAndId = sortByValue(scoresAndId);
        Map<String, Object> responseData = new HashMap<>();
        List<String> urls = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
            Integer audioId = entry.getKey();
            urls.add(relaxationTechniquesService.SearchUrlOfAudioIdMethod(audioId));
        }
        int i=0;
        for (String url : urls) {
            responseData.put("techinique" + i, url);
        }
        ResponseEntity.ok(responseData);
    }

    private int getUserID(String user) {
        int userId = -1;
        userId = userService.SearchUserTAGMethod(user); // Busca por username
        if (userId == -1) {
            userId = userService.SearchUsersByEmailMethod(user);// Si no lo encuentra busca por email
        }
        return userId;
    }

    private char getAgeRange(int age) {
        char ageRange = 't';
        if (age < 14) {
            ageRange = 'n';
        } else if (age >= 60) {
            ageRange = 'y';
        }
        return ageRange;
    }

    public double getCalification(int userTAGId, int audioId, int levelTAGId, int age) {
        String namePreferenceAuditive = relaxationTechniquesService.SearchPreferenceAuditiveOfAudioMethod(audioId);
        String namePreferenceSensorial = relaxationTechniquesService.SearchPreferenceSensorialOfAudioMethod(audioId);
        Double calificationPersonal = listenedAudiosFeedbackService.SearchScoreOfAudioMethod(userTAGId, audioId);
        if(calificationPersonal == null)
        {
            calificationPersonal = 5.0;
        }
        Integer scorePreferenceAuditive = null;
        scorePreferenceAuditive = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, namePreferenceAuditive);
        if(scorePreferenceAuditive == null)
        {
            scorePreferenceAuditive = 70;
        }
        Integer scorePreferenceSensorial = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId, namePreferenceSensorial);
        if(scorePreferenceSensorial == null)
        {
            scorePreferenceSensorial = 70;
        }
        double scoreSimilarUsers = CalificationSimilarsUsers(age, levelTAGId, audioId);
        return ((calificationPersonal * 45) / 5) + ((scorePreferenceAuditive * 25) / 100)
                + ((scorePreferenceSensorial * 20) / 100) + ((scoreSimilarUsers * 10) / 5);
    }

    public double CalificationSimilarsUsers(int age, int levelTAGId, int audioId) {
        double finalScore = 2.5;
        try {
            List<Integer> usersTAGSimilarsId = userTAGService.SearchUsersSimilarsId(age, levelTAGId);
            int quantity = 0, calificationsAcumulated = 0;
            for (Integer userTAGId : usersTAGSimilarsId) {
                double calificationPersonal = listenedAudiosFeedbackService.SearchScoreOfAudioMethod(userTAGId,
                        audioId);
                quantity++;
                calificationsAcumulated += calificationPersonal;
            }
            finalScore = calificationsAcumulated / quantity;
        } catch (NullPointerException ex) {
            // No hay usuarios similares para calcular la calificación
        }
        return finalScore;
    }

    private Map<Integer, Double> sortByValue(Map<Integer, Double> scoresAndId) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(scoresAndId.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<Integer, Double> sortedMap = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


    
    @GetMapping("prueba")
    public String postMethodName() {
        List<Integer> ids = new ArrayList<>();
        ids = relaxationTechniquesService.allAudiosMinor5MinMethod();
        String entity = "";
        for (Integer integer : ids) {
            System.out.println(integer);
            entity += String.valueOf(integer);
        }
        return entity;
    }
}
