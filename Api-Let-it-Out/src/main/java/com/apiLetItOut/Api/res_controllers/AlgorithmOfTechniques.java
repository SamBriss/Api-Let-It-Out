package com.apiLetItOut.Api.res_controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ListenedAudiosFeedbackService;
import com.apiLetItOut.Api.services.PreferencesTAGUserService;
import com.apiLetItOut.Api.services.RelaxationTechniquesService;
import com.apiLetItOut.Api.services.TechniquesDownloadService;
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
    @Autowired
    TechniquesDownloadService techniquesDownloadService;

    @PostMapping("searchUrls")
    public ResponseEntity<Map<String, Object>> getTechniques(@RequestParam("user") String user) {
        int userId = getUserID(user);
        Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
        Map<String, Object> responseData = new HashMap<>();
        List<String> urls = techniquesDownloadService.SearchUrlOdAudiosToDownloadMethod(userTAGId, LocalDate.now());
        int i = 0;
        for (String url : urls) {
            responseData.put("url" + i, url);
            i++;
        }
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("updateDownload")
    public ResponseEntity<String> downloadComplete(@RequestParam("user") String user,
            @RequestParam("completed") String completedStr) {
        int userId = getUserID(user);
        Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
        LocalDate today = LocalDate.now();
        Integer completed = 0;
        try {
            completed = Integer.parseInt(completedStr);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo convertir el valor a int");
        }
        if (userTAGId != null) {
            List<Integer> audiosId = new ArrayList<>();
            int verification = 0;
            audiosId = techniquesDownloadService.SearchAudioIdByDateMethod(userTAGId, today);
            if (audiosId != null) {
                for (int i = 0; i < completed; i++) {
                    int audioId = audiosId.get(i);
                    verification = techniquesDownloadService.CheckCompleteAudioId(userTAGId, today, audioId);
                }
                if (verification > 0) {
                    System.out.println("success");
                    return ResponseEntity.status(HttpStatus.OK).body("success");
                } else {
                    System.out.println("no se pudo cambiar el estado");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("no se pudo cambiar el estado de descarga");
                }
            } else {
                System.out.println("no se pudo encontrar los audiosId");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error interno");
            }
        } else {
            System.out.println("no se pudo encontrar el usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo encontrar el usuario");
        }

    }

    @PostMapping("searchUrlsDownloaded")
    public ResponseEntity<Map<String, Object>> UrlDownloadComplete(@RequestParam("user") String user) {
        int userId = getUserID(user);
        Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
        if (userTAGId != null) {
            List<String> urlsOfAudiosDownloaded = techniquesDownloadService.SearchUrlOdAudiosDownloaded(userTAGId);
            if (urlsOfAudiosDownloaded != null) {
                Map<String, Object> responseData = new HashMap<>();
                int i = 0;
                for (String url : urlsOfAudiosDownloaded) {
                    responseData.put("url" + i, url);
                    i++;
                }
                if (responseData != null && !responseData.isEmpty()) {
                    return ResponseEntity.ok(responseData);
                } else {
                    return ResponseEntity.ok(null);
                }
            } else {
                return ResponseEntity.ok(null);
            }
        } else {
            return ResponseEntity.ok(null);
        }

    }

    public Map<String, Object> getUrlsOfTechniques(String user) {
        int userId = getUserID(user);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        int age = userService.SearchUserAgeMethod(userId);
        char ageRange = getAgeRange(age);
        int levelTAGId = userTAGService.SearchLevelTAGMethod(userTAGId);
        String genderStr = userService.SearchUserGenderMethod(userId);
        char gender = genderStr.charAt(0);
        List<Integer> usersTAGSimilarsId = new ArrayList<>();
        try {
            usersTAGSimilarsId = userTAGService.SearchUsersSimilarsId(age, levelTAGId);
        } catch (NullPointerException ex) {
        }
        Map<Integer, Double> scoresAndId = new HashMap<>();
        List<Integer> firstFilterAudios = relaxationTechniquesService.allAudiosMinor5MinMethod();
        for (Integer audioId : firstFilterAudios) {
            double calificationOfAudio = getCalification(userTAGId, audioId, usersTAGSimilarsId);
            scoresAndId.put(audioId, calificationOfAudio);
        }
        // Ordenar el HashMap por calificación
        scoresAndId = sortByValue(scoresAndId);
        if (scoresAndId.size() > 15) // Filtro por edades
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                if (age > 44) { // Busca audios que sean activos para personas mayores
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToLifeStyleAndIdMethod(audioId,
                            'a');
                } else if (age < 14) { // Busca audios especiales para niños
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToAgeAndIdMethod(audioId,
                            ageRange);
                }
                if (filteredAudioId != null && !filteredAudioId.equals(-1)) {
                    System.out.println("filtro edad audio agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            // Eliminar las claves marcadas para eliminación
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("el audioId es: " + key + " la calificacion es: " + value);
            }
            scoresAndId = sortByValue(scoresAndId);
            System.out.println("");
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("el audioId es: " + key + " la calificacion es: " + value);
            }
        }
        if (scoresAndId.size() > 15)// Busca tecnicas que tengan una calificacion menor a 3
        {

            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = listenedAudiosFeedbackService.SearchIdByScore(userTAGId, audioId);
                if (filteredAudioId != null) {
                    System.out.println("filtro cal personal agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }

            // Eliminar las claves marcadas para eliminación
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Por estilo de vida
        {
            List<Integer> audiosPassivesId = new ArrayList<>();
            int i = 0;
            Map<Integer, Double> audiosPassives = new HashMap<>();
            String lifeStylePrefered = preferencesTAGUserService.SearchLifeStylePreference(userTAGId, 5);
            lifeStylePrefered = lifeStylePrefered.toLowerCase();
            char auditory = lifeStylePrefered.charAt(0);
            if (lifeStylePrefered.equals("Activo")) {
                for (Integer audioId : scoresAndId.keySet()) {
                    if (i < 6) {
                        Integer audioIdPassive = relaxationTechniquesService
                                .SearchAudioByIdAndDifferentAuditoryMethod(audioId, 'a');
                        if (audioIdPassive != null) {
                            System.out
                                    .println("audios pasivos para activos agregado a keysToRemove: " + audioIdPassive);
                            audiosPassivesId.add(audioIdPassive);
                            i++;
                        }

                    }
                }
                for (Integer audioId : audiosPassivesId) {
                    double calificationOfAudio = getCalification(userTAGId, audioId, usersTAGSimilarsId);
                    audiosPassives.put(audioId, calificationOfAudio);
                }
                audiosPassives = sortByValue(audiosPassives);
                // scoresAndId.putAll(audiosPassives);
            }
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentAuditoryMethod(audioId,
                        auditory);
                if (filteredAudioId != null) {
                    System.out.println("audios activos o pasivos agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Filtro por hora del día
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToHourAndId(audioId, 't');
                if (filteredAudioId != null) {
                    System.out.println("audios todo el día agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Filtro de preferencias auditivas con 0
        {
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> preferencesAuditives0 = preferencesTAGUserService
                    .SearchPreferenceAuditive0ScoreMethod(userTAGId, 1);
            for (String preference : preferencesAuditives0) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService
                            .SearchAudioByPreferenceAuditive0AndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        System.out.println("0 auditivas agregado a keysToRemove: " + filteredAudioId);
                        keysToRemove.add(audioId);
                    }
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Filtro por preferencias sensoriales menos gustadas
        {
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> lastPreferencesSensoriales = preferencesTAGUserService
                    .SearchLastPreferencesSensoriales(userTAGId, 2);
            for (String preference : lastPreferencesSensoriales) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService
                            .SearchAudioByPreferenceSensorialAndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        System.out.println("sensoriales ultimas agregado a keysToRemove: " + filteredAudioId);
                        keysToRemove.add(audioId);
                    }
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Filtro de calificaciones de usuarios similares
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Double scoresSimilarsUsers = CalificationSimilarsUsers(audioId, usersTAGSimilarsId);
                if (scoresSimilarsUsers != null && scoresSimilarsUsers < 2.5 && scoresSimilarsUsers > 0.0) {
                    System.out.println(
                            "cal similares agregado a keysToRemove: " + scoresSimilarsUsers + " y audioId" + audioId);
                    keysToRemove.add(audioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            scoresAndId = sortByValue(scoresAndId);
        }
        if (scoresAndId.size() > 15) // Filtro de nivel de ansieadad
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentLevelTAGMethod(audioId,
                        levelTAGId);
                if (filteredAudioId != null) {
                    System.out.println("nivelTag agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
        }
        if (scoresAndId.size() > 15)// Filtro diferente genero
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentGenderMethod(audioId, gender,
                        't');
                if (filteredAudioId != null) {
                    System.out.println("genero agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
        }
        scoresAndId = sortByValue(scoresAndId);
        Map<String, Object> responseData = new HashMap<>();
        List<String> urls = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
            Integer audioId = entry.getKey();
            String url = relaxationTechniquesService.SearchUrlOfAudioIdMethod(audioId);
            System.out.println(url);
            urls.add(url);
        }
        int i = 0;
        for (String url : urls) {
            System.out.println("entra al foreach de url " + url);
            responseData.put("url" + i, url);
            i++;
        }
        return responseData;
    }

    public boolean insertDownload(List<String> urls, String user, LocalDate date, int completed) {

        List<Integer> audiosIds = new ArrayList<>();
        for (String url : urls) {
            Integer audioId = relaxationTechniquesService.SearchAudioIdByUrl(url);
            if (audioId != null) {
                audiosIds.add(audioId);
            }
        }
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if (userTAGId == null) {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if (userTAGId != null) {
            techniquesDownloadService.deleteDownloads(userTAGId);
            for (Integer audioId : audiosIds) {
                int count = techniquesDownloadService.InsertDownloads(date, userTAGId, audioId, completed);
                if (count < 0) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("searchUrls/PracticeButtons")
    public ResponseEntity<Map<String, Object>> getTechniquesPracticeButton(@RequestParam("user") String user) {
        int userId = getUserID(user);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        int age = userService.SearchUserAgeMethod(userId);
        char ageRange = getAgeRange(age);
        int levelTAGId = userTAGService.SearchLevelTAGMethod(userTAGId);
        int levelTechiniques = userTAGService.SearchLevelTechniqueMethod(userTAGId);
        String genderStr = userService.SearchUserGenderMethod(userId);
        char gender = genderStr.charAt(0);
        List<Integer> usersTAGSimilarsId = new ArrayList<>();
        try {
            usersTAGSimilarsId = userTAGService.SearchUsersSimilarsId(age, levelTAGId);
        } catch (NullPointerException ex) {

        }
        Map<Integer, Double> scoresAndId = new HashMap<>();
        List<Integer> firstFilterAudios = relaxationTechniquesService
                .allAudiosOfLevelAndBelowUserMethod(levelTechiniques);
        for (Integer audioId : firstFilterAudios) {
            double calificationOfAudio = getCalification(userTAGId, audioId, usersTAGSimilarsId);
            scoresAndId.put(audioId, calificationOfAudio);
        }
        if (scoresAndId.size() > 15) // Filtro por edades
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                if (age > 44) { // Busca audios que sean activos para personas mayores
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToLifeStyleAndIdMethod(audioId,
                            'a');
                } else if (age < 14) { // Busca audios especiales para niños
                    filteredAudioId = relaxationTechniquesService.SearchAudioAccordingToAgeAndIdMethod(audioId,
                            ageRange);
                }
                if (filteredAudioId != null && !filteredAudioId.equals(-1)) {
                    System.out.println("filtro edad audio agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            // Eliminar las claves marcadas para eliminación
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("el audioId es: " + key + " la calificacion es: " + value);
            }
            scoresAndId = sortByValue(scoresAndId);
            System.out.println("");
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer key = entry.getKey();
                Double value = entry.getValue();
                System.out.println("el audioId es: " + key + " la calificacion es: " + value);
            }
        }

        if (scoresAndId.size() > 15) // Filtro de preferencias auditivas con 0
        {
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> preferencesAuditives0 = preferencesTAGUserService
                    .SearchPreferenceAuditive0ScoreMethod(userTAGId, 1);
            for (String preference : preferencesAuditives0) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService
                            .SearchAudioByPreferenceAuditive0AndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        System.out.println("0 auditivas agregado a keysToRemove: " + filteredAudioId);
                        keysToRemove.add(audioId);
                    }
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
        }
        if (scoresAndId.size() > 15) // Filtro por preferencias sensoriales menos gustadas
        {
            List<Integer> keysToRemove = new ArrayList<>();
            List<String> lastPreferencesSensoriales = preferencesTAGUserService
                    .SearchLastPreferencesSensoriales(userTAGId, 2);
            for (String preference : lastPreferencesSensoriales) {
                for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                    Integer audioId = entry.getKey();
                    Integer filteredAudioId = relaxationTechniquesService
                            .SearchAudioByPreferenceSensorialAndIdMethod(audioId, preference);
                    if (filteredAudioId != null) {
                        System.out.println("sensoriales ultimas agregado a keysToRemove: " + filteredAudioId);
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
        }
        if (scoresAndId.size() > 15) // Filtro de calificaciones de usuarios similares
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Double scoresSimilarsUsers = CalificationSimilarsUsers(audioId, usersTAGSimilarsId);
                if (scoresSimilarsUsers != null && scoresSimilarsUsers < 2.5 && scoresSimilarsUsers > 0.0) {
                    System.out.println(
                            "cal similares agregado a keysToRemove: " + scoresSimilarsUsers + " y audioId" + audioId);
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
        }
        if (scoresAndId.size() > 15) // Filtro de nivel de ansieadad
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentLevelTAGMethod(audioId,
                        levelTAGId);
                if (filteredAudioId != null) {
                    System.out.println("nivelTag agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 14) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
        }
        if (scoresAndId.size() > 15)// Filtro diferente genero
        {
            List<Integer> keysToRemove = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
                Integer audioId = entry.getKey();
                Integer filteredAudioId = null;
                filteredAudioId = relaxationTechniquesService.SearchAudioByIdAndDifferentGenderMethod(audioId, gender,
                        't');
                if (filteredAudioId != null) {
                    System.out.println("genero agregado a keysToRemove: " + filteredAudioId);
                    keysToRemove.add(filteredAudioId);
                }
            }
            for (Integer key : keysToRemove) {
                if (scoresAndId.size() > 15) {
                    scoresAndId.remove(key);
                } else {
                    break;
                }
            }
        }
        Map<String, Object> responseData = new HashMap<>();

        int i = 0;
        for (Map.Entry<Integer, Double> entry : scoresAndId.entrySet()) {
            Integer audioId = entry.getKey();
            String url = relaxationTechniquesService.SearchUrlOfAudioIdMethod(audioId);
            System.out.println("entra al foreach de url " + url);
            responseData.put("url" + i, url);
            responseData.put("audioId" + i, audioId);
            i++;
        }
        return ResponseEntity.ok(responseData);
    }

    public double getCalification(int userTAGId, int audioId, List<Integer> usersTAGIdSimilars) {
        Double scoreSimilarUsers;
        // Obtiene nombres de preferencias auditivas y sensoriales favoritas del usuario
        String namePreferenceAuditive = relaxationTechniquesService.SearchPreferenceAuditiveOfAudioMethod(audioId);
        String namePreferenceSensorial = relaxationTechniquesService.SearchPreferenceSensorialOfAudioMethod(audioId);
        // Busca la calificacion de la preferencia
        Integer scorePreferenceAuditive = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId,
                namePreferenceAuditive);
        if (scorePreferenceAuditive == null) {
            scorePreferenceAuditive = 0;
        }
        Integer scorePreferenceSensorial = preferencesTAGUserService.SearchScoreOfPreferenceUserTAG(userTAGId,
                namePreferenceSensorial);
        if (scorePreferenceSensorial == null) {
            scorePreferenceSensorial = 0;
        }
        // Busca el promedio de calificacion personal del audio
        Double calificationPersonal = listenedAudiosFeedbackService.SearchScoreOfAudioMethod(userTAGId, audioId);
        if (calificationPersonal == null) {
            calificationPersonal = 5.0;
        }
        try {
            scoreSimilarUsers = CalificationSimilarsUsers(audioId, usersTAGIdSimilars);
        } catch (NullPointerException ex) {
            scoreSimilarUsers = 2.5;
        }

        return ((calificationPersonal * 45) / 5) + ((scorePreferenceAuditive * 25) / 100)
                + ((scorePreferenceSensorial * 20) / 100) + ((scoreSimilarUsers * 10) / 5);
    }

    public Double CalificationSimilarsUsers(int audioId, List<Integer> usersTAGSimilarsId) {
        double finalScore = 2.5;
        System.out.println(audioId + " el audio recibido");
        if (usersTAGSimilarsId != null && !usersTAGSimilarsId.isEmpty()) {
            int quantity = 0, calificationsAcumulated = 0;
            for (Integer userTAGId : usersTAGSimilarsId) {
                try {
                    Double calificationPersonal = listenedAudiosFeedbackService.SearchScoreOfAudioMethod(userTAGId,
                            audioId);
                    if (calificationPersonal != null) {
                        calificationPersonal = calificationPersonal.doubleValue();
                        quantity++;
                        calificationsAcumulated += calificationPersonal;
                    }
                } catch (NullPointerException ex) {
                    continue;
                }

            }
            if (calificationsAcumulated != 0) {
                finalScore = calificationsAcumulated / quantity;
            }

        }
        return finalScore;
    }

    private Map<Integer, Double> sortByValue(Map<Integer, Double> scoresAndId) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(scoresAndId.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                // Orden descendente
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<Integer, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
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
