package com.apiLetItOut.Api.res_controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ActivitiesFromTherapistService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class ActivitiesFromTherapistController {
    @Autowired
    ActivitiesFromTherapistService activitiesFromTherapistService;

    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserTherapistService userTherapistService;
    
    @PostMapping("/userTherapist/ActivitiesFromTherapist")
    public ResponseEntity postMethodName(@RequestParam("usernameTherapist") String usernameTherapist,
                                        @RequestParam("usernameTAG") String usernameTAG,
                                        @RequestParam("label") String label,
                                        @RequestParam("description") String description,
                                        @RequestParam("dateAsign") String dateAsignstr,
                                        @RequestParam("dateMax") String dateMaxstr,
                                        @RequestParam("completed") String completedstr) throws ParseException {

        int userIdTTAG = userService.SearchUserTAGMethod(usernameTAG);
        int userTAGId = userTAGService.FindUserTAGMethod(userIdTTAG);

        int userIdTherapist = userService.SearchUserTAGMethod(usernameTherapist);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdTherapist);

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date dateAsign = formatoFecha.parse(dateAsignstr);

        Date dateMax = formatoFecha.parse(dateMaxstr);

        int completed;

        try {
            completed = Integer.parseInt(completedstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        if (userTAGId > 0) 
        {
            int RegisterActivity = activitiesFromTherapistService.RegisterNewActivityFromTherapistMethod(userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed);
            
            if (RegisterActivity > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("sucess");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar vinculacion");
            }
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    @PostMapping("/userTAG/RequestQuantityActivities")
    public ResponseEntity postMethodNameCountActivities(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            int countRequest = activitiesFromTherapistService.CountRequestQuantityActivitiesMethod(userTAGId);

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

    @PostMapping("/userTherapist/ViewActivitiesIds")
    public ResponseEntity postMethodRquestIds(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            List<Integer> ActivitiesIds = activitiesFromTherapistService.SelectActivityIdMethod(userTAGId);
            if (!ActivitiesIds.isEmpty()) {
                List<String> ActivitiesIdsString = new ArrayList<>();
                for (int ActivityId : ActivitiesIds) {
                    String id = String.valueOf(ActivityId);
                    if (id != null) {
                        ActivitiesIdsString.add(id);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al volver string");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(ActivitiesIdsString);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay diarios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTherapist/TherapistUsername")
    public ResponseEntity postMethodRquest(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        
        if(userTAGId > 0)
        {
            List<Integer> userTherapistIds =  activitiesFromTherapistService.SelectTherapistIdMethod(userTAGId);

            if (!userTherapistIds.isEmpty()) {
                List<String> usernames = new ArrayList<>();
                for (int userTherapistId : userTherapistIds) {
                    int userIdT = userTherapistService.SearchIdByTherapistMethod(userTherapistId);
                    String usernameT = userService.SearchUsernameByIdMethod(userIdT);
                    if (usernameT != null) {
                        usernames.add(usernameT);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar en username");
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(usernames);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay terapeuta");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error usuario TAG");
        }
    }
    @PostMapping("/userTherapist/updateActivityComments")
    public ResponseEntity<String> updateTherapistVinculationCode(@RequestParam("activityTId") String activityTIdstr,
                                                                @RequestParam("comments") String comments){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int updateActivity = activitiesFromTherapistService.UpdateCommentsTherapistMethod(activityTId, comments);

        if(updateActivity > 0)
        {
            return ResponseEntity.status(HttpStatus.OK).body("update");
        }
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No update");
        }

    }

}
