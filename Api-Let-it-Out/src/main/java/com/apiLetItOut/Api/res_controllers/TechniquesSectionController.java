package com.apiLetItOut.Api.res_controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

import com.apiLetItOut.Api.services.ListenedAudiosFeedbackService;
import com.apiLetItOut.Api.services.PreferencesTAGUserService;
import com.apiLetItOut.Api.services.RelaxationTechniquesService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;


@RestController
@RequestMapping("api/techniquesSection")
public class TechniquesSectionController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    ListenedAudiosFeedbackService listenedAudiosFeedbackService;

    @Autowired
    RelaxationTechniquesService relaxationTechniquesService;

    @Autowired
    PreferencesTAGUserService preferencesTAGUserService;
    
    @PostMapping("/getData")
    public ResponseEntity<Map<String, Object>> getDataUserTAG(@RequestParam("user") String user)
    {
        Integer age = 0, levelTechnique = 0;
        int category = 1, countTotalProgress=0, countTimesListened = 0;
        double progressOfLevel=0.0;
        Map<String, Object> responseData = new HashMap<>();
        Integer userId = userService.SearchUserTAGMethod(user);
        List<Integer> audiosId = new ArrayList<>();
        if(userId==null)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        if(userId!=null)
        {
            age  = userService.SearchUserAgeMethod(userId);
            if(age>0)
            {
                responseData.put("age", age);
            }
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId!=null)
            {
                levelTechnique = userTAGService.SearchLevelTechniqueMethod(userTAGId);
                if(levelTechnique>0 && levelTechnique!=null)
                {
                    responseData.put("levelTechnique", levelTechnique);
                    if(levelTechnique==1 && age>13)
                    {
                        category = 1;
                    } else if(levelTechnique==2 && age>13)
                    {
                        category = 2;
                    } else if(levelTechnique==3 && age>13)
                    {
                        category = 3;
                    }
                    else if(levelTechnique==1 && age<14)
                    {
                        category = 4;
                    } else if(levelTechnique==2 && age<14)
                    {
                        category = 5;
                    } else if(levelTechnique==3 && age<14)
                    {
                        category = 6;
                    }

                    audiosId = fillListAudiosId(category);
                    for (Integer audioId : audiosId) {
                        countTimesListened = listenedAudiosFeedbackService.CountTimesAudioListenedMethod(userTAGId, audioId);
                        if(countTimesListened>0)
                        {
                            countTotalProgress++;
                        }
                    }
                    progressOfLevel = (countTotalProgress * 100) / audiosId.size();
                    responseData.put("progressLevel", progressOfLevel);
                }
            }
        }
        if(responseData.isEmpty())
        {
            return ResponseEntity.badRequest().body(responseData);
        } 
        else
        {
            return ResponseEntity.ok(responseData);
        }
    }

    @PostMapping("/registerListenedNotComplete")
    public ResponseEntity<String> RegisterTechniqueListenedNotComplete(@RequestParam("user") String user, 
                                                    @RequestParam("audioId") String audioIdStr,
                                                    @RequestParam("progress") String progressStr,
                                                    @RequestParam("score") String scoreStr)
    {
        Integer audioId=0, score=0;
        Long progressLong=0L;
        try{
            audioId = Integer.parseInt(audioIdStr);
            progressLong = Long.parseLong(progressStr);
            score = Integer.parseInt(scoreStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        LocalDate feedbackDate = LocalDate.now();
        if(score < 0)
        {
            score = 2;
        }
        LocalTime duration = relaxationTechniquesService.SearchDurationByAudioIdMethod(audioId);
        long durationMillis = duration.toSecondOfDay() * 1000;
        // Calcular el porcentaje de progreso
        Integer progressValue = (int) ((progressLong * 100) / durationMillis);
        String progress = String.valueOf(progressValue);
        Integer userId = userService.SearchUserTAGMethod(user);
        if(userId==null)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        if(userId!=null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId !=null)
            {
                int rows = listenedAudiosFeedbackService.RegisterTechniqueListenedMethod(userTAGId, audioId, progress, score, feedbackDate);
                if(rows==1)
                {
                    return new ResponseEntity<>("Se registro de manera correcta", HttpStatus.ACCEPTED);
                } else 
                {
                    return new ResponseEntity<>("No se registro de manera correcta", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else{
                return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
            }
        } else{
            return new ResponseEntity<>("No se encontro el userId", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerListenedComplete")
    public ResponseEntity<String> RegisterTechniqueListenedComplete(@RequestParam("user") String user, 
                                                    @RequestParam("audioId") String audioIdStr,
                                                    @RequestParam("score") String scoreStr)
    {
        Integer score = 0;
        Integer audioId = 0;
        try{
            audioId = Integer.parseInt(audioIdStr);
            score = Integer.parseInt(scoreStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        LocalDate feedbackDate = LocalDate.now();
        String progress = String.valueOf(100);
        Integer userId = userService.SearchUserTAGMethod(user);
        if(userId==null)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        if(userId!=null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId !=null)
            {
                int rows = listenedAudiosFeedbackService.RegisterTechniqueListenedMethod(userTAGId, audioId, progress, score, feedbackDate);
                if(rows==1)
                {
                    return new ResponseEntity<>("Se registro de manera correcta", HttpStatus.ACCEPTED);
                } else 
                {
                    return new ResponseEntity<>("No se registro de manera correcta", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else{
                return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
            }
        } else{
            return new ResponseEntity<>("No se encontro el userId", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updatePreferencesLevel")
    public ResponseEntity<String> UpdatePreferences(@RequestParam("user") String user, 
                                                @RequestParam("name") String name, 
                                                @RequestParam("score") String scoreStr)
    {
        int score;
        try{
            score  = Integer.parseInt(scoreStr);
        } catch(NumberFormatException ex)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        Integer userId = userService.SearchUserTAGMethod(user);
        if(userId==null)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        if(userId!=null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId!=null)
            {
                int rows = preferencesTAGUserService.UpdatePreferencesUserMethod(userTAGId, name, score);
                if(rows > 0)
                {
                    return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
                }
                else
                {
                    return new ResponseEntity<>("No se modifico el valor", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else
            {
                return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
            }
        }
        else
        {
            return new ResponseEntity<>("No se encontro el userId", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/levelUp")
    public ResponseEntity<String> UpdateLevel(@RequestParam("user") String user) {
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if(userTAGId==null)
        {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if(userTAGId!=null)
        {
            Integer levelTechniqueActual = userTAGService.SearchLevelTechniqueMethod(userTAGId);
            if(levelTechniqueActual!=null)
            {
                if(levelTechniqueActual==1 || levelTechniqueActual==2)
                {
                    levelTechniqueActual++;
                }
                Integer rows = userTAGService.UpdateUserTAGLevelTechniques(userTAGId, levelTechniqueActual);
                if(rows>0)
                {
                    return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
                } else{
                    return new ResponseEntity<>("No se modifico el valor", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                return new ResponseEntity<>("No se encontro el nivel", HttpStatus.BAD_REQUEST);
            }
        } else{
            return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
        }

        
    }
    

    public List<Integer> fillListAudiosId(int levelAge)
    {
        List<Integer> audiosId = new ArrayList<>();
        switch (levelAge) {
            case 1:
            // mayores nivel 1
                audiosId.add(45);
                /*audiosId.add(47);
                audiosId.add(72);
                audiosId.add(32);
                audiosId.add(78);
                audiosId.add(60);
                audiosId.add(81);
                audiosId.add(17);*/
                break;
            case 2:
            // mayores nivel 2
                audiosId.add(21);
                audiosId.add(23);
                audiosId.add(75);
                audiosId.add(79);
                audiosId.add(82);
                audiosId.add(85);
                break;
            case 3:
            //mayores nivel 3
                audiosId.add(80);
                audiosId.add(66);
                audiosId.add(56);
                audiosId.add(71);
                audiosId.add(29);
                audiosId.add(35);
                break;
            case 4:
            // menores nivel 1
                audiosId.add(42);
                audiosId.add(78);
                audiosId.add(60);
                audiosId.add(64);
                audiosId.add(70);
                audiosId.add(51);
                break;
            case 5:
            // menores nivel 2
                audiosId.add(87);
                audiosId.add(85);
                audiosId.add(61);
                audiosId.add(82);
                audiosId.add(88);
                audiosId.add(84);
                break;
            default:
                audiosId.add(54);
                audiosId.add(83);
                audiosId.add(58);
                audiosId.add(18);
                audiosId.add(36);
                audiosId.add(41);
                break;
        }
        return audiosId;
    }
}
