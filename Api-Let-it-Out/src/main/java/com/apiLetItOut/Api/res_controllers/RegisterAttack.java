package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.apiLetItOut.Api.services.AttackRegistersService;
import com.apiLetItOut.Api.services.ListenedAudiosFeedbackService;
import com.apiLetItOut.Api.services.RelaxationTechniquesService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api/anxietyButton/")
public class RegisterAttack {
    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @Autowired
    RelaxationTechniquesService relaxationTechniquesService;

    @Autowired
    ListenedAudiosFeedbackService listenedAudiosFeedbackService;

    @Autowired
    AttackRegistersService attackRegistersService;


    @PostMapping("registerDataAttack")
    public ResponseEntity<String> RegisterAttack(@RequestParam("user") String user, 
                                @RequestParam("startHour") String startHourStr, 
                                @RequestParam("endHour") String endHourStr, 
                                @RequestParam("type") String typeStr)
    {
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        
        if(userTAGId == null)
        {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if(userTAGId!=null)
        {
            Integer typeId;
            try{
                
                typeId = Integer.parseInt(typeStr);
            } catch(NumberFormatException ex)
            {
                return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            LocalTime startHour = LocalTime.parse(startHourStr);
            LocalTime endHour = LocalTime.parse(endHourStr);
            // Calcular la duración en segundos
            long durationInSeconds = ChronoUnit.SECONDS.between(startHour, endHour);

            // Calcular las horas, minutos y segundos
            long hours = durationInSeconds / 3600;
            long minutes = (durationInSeconds % 3600) / 60;
            long seconds = durationInSeconds % 60;

            // Crear un nuevo objeto LocalTime con las horas, minutos y segundos calculados
            LocalTime duration = LocalTime.of((int) hours, (int) minutes, (int) seconds);

            LocalDate date = LocalDate.now();

            Integer rows = attackRegistersService.RegisterAttackMethod(date, startHour, endHour, duration, 0, typeId, userTAGId);
            if(rows > 0 && rows!=null)
            {
                Integer attackRegisterId = attackRegistersService.SearchAttackIdMethod(date, startHour, endHour, userTAGId);
                if(attackRegisterId!=null)
                {
                    return new ResponseEntity<>(String.valueOf(attackRegisterId), HttpStatus.OK);
                } else{
                    return new ResponseEntity<>("No se encontro el registro del ataque", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else{
                return new ResponseEntity<>("No se registro el ataque", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PostMapping("registerTechniques")
    public ResponseEntity<String> RegisterAudiosOfAttack(@RequestParam("user") String user, 
                                                    @RequestParam("url") String url,
                                                    @RequestParam("progress") String progressStr,
                                                    @RequestParam("score") String scoreStr, 
                                                    @RequestParam("startHour")String startHourStr,
                                                    @RequestParam("endHour") String endHourStr)
    {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("entra en register audios of attack");

        Integer score=0;
        Long progressLong=0L;
        try{
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
        LocalTime duration;
        Integer audioId, progressValue;
        String progress;
        try
        {
            audioId = relaxationTechniquesService.SearchAudioIdByUrl(url);
            duration = relaxationTechniquesService.SearchDurationByAudioIdMethod(audioId);
            long durationMillis = duration.toSecondOfDay() * 1000;
            // Calcular el porcentaje de progreso
            progressValue = (int) ((progressLong * 100) / durationMillis);
            progress = String.valueOf(progressValue);
        }catch(NullPointerException ex)
        {
            return new ResponseEntity<String>("No se encontro el audioId", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        

        LocalTime startHour = LocalTime.parse(startHourStr);
        LocalTime endHour = LocalTime.parse(endHourStr);
        Integer userId = userService.SearchUserTAGMethod(user);
        if(userId==null)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        if(userId!=null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            Integer attackRegisterId = attackRegistersService.SearchAttackIdMethod(feedbackDate, startHour, endHour, userTAGId);
            if(userTAGId !=null && attackRegisterId!=null)
            {
                int rows = listenedAudiosFeedbackService.RegisterTechniquesOfAttack(userTAGId, audioId, progress, score, feedbackDate, attackRegisterId);
                if(rows==1)
                {
                    System.out.println("se registró de manera correcta");
                    return new ResponseEntity<>("Se registro de manera correcta", HttpStatus.ACCEPTED);
                } else 
                {
                    System.out.println("no se registro");
                    return new ResponseEntity<>("No se registro de manera correcta", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else if(userTAGId==null){
                System.out.println("no se encontró el usertag id");
                return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
            } else
            {
                System.out.println("no se encontró el ataque registrado");
                return new ResponseEntity<>("No se encontro el ataque registrado.", HttpStatus.BAD_REQUEST);
            }
        } else{
            System.out.println("no se encontro el userid");
            return new ResponseEntity<>("No se encontro el userId", HttpStatus.BAD_REQUEST);
        }
    }
}
