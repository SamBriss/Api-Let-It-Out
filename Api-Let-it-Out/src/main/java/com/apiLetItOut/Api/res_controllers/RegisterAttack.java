package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.apiLetItOut.Api.services.AttackRegisterDetailsService;
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

    @Autowired
    AttackRegisterDetailsService attackRegisterDetailsService;

    @PostMapping("registerDataAttack")
    public ResponseEntity<String> RegisterAttackAnxietyButton(@RequestParam("user") String user, 
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
                    return new ResponseEntity<>("Se registro de manera correcta", HttpStatus.ACCEPTED);
                } else 
                {
                    return new ResponseEntity<>("No se registro de manera correcta", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else if(userTAGId==null){
                return new ResponseEntity<>("No se encontro el userTAGId", HttpStatus.BAD_REQUEST);
            } else
            {
                return new ResponseEntity<>("No se encontro el ataque registrado.", HttpStatus.BAD_REQUEST);
            }
        } else{
            return new ResponseEntity<>("No se encontro el userId", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("getRegistersAttackIncompleted")
    public ResponseEntity SearchAttacksIncompletedController(@RequestParam("username") String username)
    {
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        if(userTAGId != null){
            List<Object[]> responseData = attackRegistersService.SearchAttacksOfUserIncompletedMethod(userTAGId);
            if(responseData!=null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(responseData);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no hay resultados");
            }
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no existe ese username");
        }
    }

    @PostMapping("QuantityRegistersAttackIncompleted")
    public ResponseEntity QuantityRegistersAttackIncompletedController(@RequestParam("username") String username){

        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);

        if(userTAGId != null)
        {
            int countRequest = attackRegistersService.QuantityAttacksOfUserIncompletedMethod(userTAGId);

            if (countRequest > 0)
            {
                String count = String.valueOf(countRequest);
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            else if (countRequest == 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error numero no valido");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("AttacksDetails")
    public ResponseEntity<String> RegisterNewManualAttacksRegister(@RequestParam("username") String username,
            @RequestParam("attackRegisterId") String attackRegisterIdstr,
            @RequestParam("place") String place,
            @RequestParam("motive") String motive,
            @RequestParam("explanaitionResume") String explanaitionResume,
            @RequestParam("intensity") String intensitystr,
            @RequestParam("emotions") String emotions,
            @RequestParam("physicalSensations") String physicalSensations,
            @RequestParam("thoughts") String thoughts,
            @RequestParam("typeOfThought") String typeOfThought,
            @RequestParam("attackMethodsId") String attackMethodsIdstr) throws ParseException {

        int attackMethodsId;
        int intensity;
        int attackRegisterId;
        String reportURL = "no";
        Double doubleattack;

        try {
            doubleattack = Double.valueOf(attackRegisterIdstr);
            attackRegisterId =Integer.valueOf(doubleattack.intValue());
            attackMethodsId = Integer.parseInt(attackMethodsIdstr);
            intensity = Integer.parseInt(intensitystr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {
            int manualAttackCout = attackRegisterDetailsService.RegisterNewAttackDetailsMethod(attackRegisterId, place, motive, place, intensity, emotions, physicalSensations, thoughts, typeOfThought, attackMethodsId, reportURL);
            if (manualAttackCout > 0) {
                Integer update = attackRegistersService.UpdateCompletedattackRegisterMethod(attackRegisterId);
                if(update != null){
                    return ResponseEntity.status(HttpStatus.OK).body("success");
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    @PostMapping("InformationOfThatAttack")
    public ResponseEntity DatosDiarioParaTerapeuta(@RequestParam("attackRegisterId") String attackRegisterIdstr){

        int attackRegisterId;
        Double doubleattack;
        try {
            doubleattack = Double.valueOf(attackRegisterIdstr);
            attackRegisterId =Integer.valueOf(doubleattack.intValue());
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        if(attackRegisterId > 0)
        {
            List<Object[]> informacion = listenedAudiosFeedbackService.SearchInformationOfTechniqueIncompleteMethod(attackRegisterId);
            if (!informacion.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.OK).body(informacion);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay audios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("QuantityAudiosIncompleted")
    public ResponseEntity QuantityAudiosController(@RequestParam("attackRegisterId") String attackRegisterIdstr){

        int attackRegisterId;
        Double doubleattack;
        try {
            doubleattack = Double.valueOf(attackRegisterIdstr);
            attackRegisterId =Integer.valueOf(doubleattack.intValue());
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        if(attackRegisterId > 0)
        {
            int countRequest = listenedAudiosFeedbackService.SelectQuantityAudiosMethod(attackRegisterId);

            if (countRequest > 0)
            {
                String count = String.valueOf(countRequest);
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            else if (countRequest == 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error numero no valido");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    
    @PostMapping("UpdateScoreCompleteFeedback")
    public ResponseEntity UpdateScoreCompleteFeedbackController(@RequestParam("score") String scoreStr,
                                                                @RequestParam("listenedId") String listenedIdstr){

    int listenedId;
    Double DoublelistenedId;
    int score;
    try {
        score = Integer.parseInt(scoreStr);
        DoublelistenedId = Double.valueOf(listenedIdstr);
        listenedId =Integer.valueOf(DoublelistenedId.intValue());
    } catch (NumberFormatException e) {
        return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                HttpStatus.BAD_REQUEST);
    }
        if(listenedId > 0)
        {
            int register = listenedAudiosFeedbackService.UpdateCompletedfeedbackaudiosAttacks(score, listenedId);

            if (register > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("success");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de registro");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el id de listened");
        }
    }
}
