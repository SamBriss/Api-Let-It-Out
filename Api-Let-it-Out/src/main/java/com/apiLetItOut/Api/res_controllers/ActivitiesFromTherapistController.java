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
import com.apiLetItOut.Api.services.DocumentsService;
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

    @Autowired
    DocumentsService documentsService;
    
    @PostMapping("/userTherapist/ActivitiesFromTherapist")
    public ResponseEntity postMethodName(@RequestParam("usernameTherapist") String usernameTherapist,
                                        @RequestParam("usernameTAG") String usernameTAG,
                                        @RequestParam("label") String label,
                                        @RequestParam("description") String description,
                                        @RequestParam("dateAsign") String dateAsignstr,
                                        @RequestParam("dateMax") String dateMaxstr,
                                        @RequestParam("completed") String completedstr,
                                        @RequestParam("document") String document) throws ParseException {

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
            int RegisterActivity = activitiesFromTherapistService.RegisterNewActivityFromTherapistMethod
            (userTAGId, userTherapistId, label, description, dateAsign, dateMax, completed, document);
            
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
    public ResponseEntity<String> updateTherapistComment(@RequestParam("activityTId") String activityTIdstr,
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
    @PostMapping("/userTAG/RequestQuantityActivitiesPendientes")
    public ResponseEntity postMethodNameCountActivitiesPendientes(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 0;

        if(userTAGId > 0)
        {
            int countRequest = activitiesFromTherapistService.CountRequestQuantityActivitiesCompletedMethod(userTAGId, completed);

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
    @PostMapping("/userTAG/RequestQuantityActivitiesCompletado")
    public ResponseEntity postMethodNameCountActivitiesCompletado(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 1;

        if(userTAGId > 0)
        {
            int countRequest = activitiesFromTherapistService.CountRequestQuantityActivitiesCompletedMethod(userTAGId, completed);

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
    @PostMapping("/userTAG/ViewActivitiesIdsPendiente")
    public ResponseEntity postMethodRquestIdsPendiente(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 0;

        if(userTAGId > 0)
        {
            List<Integer> ActivitiesIds = activitiesFromTherapistService.SelectActivityIdCompletedMethod(userTAGId, completed);
            if (!ActivitiesIds.isEmpty()) {
                List<String> ActivitiesIdsString = new ArrayList<>();
                for (int ActivityId : ActivitiesIds) {
                    String id = String.valueOf(ActivityId);
                    if (id != null) {
                        ActivitiesIdsString.add(id);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(ActivitiesIdsString);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
    @PostMapping("/userTAG/ViewActivitiesIdsCompletado")
    public ResponseEntity postMethodRquestIdsCompleted(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 1;

        if(userTAGId > 0)
        {
            List<Integer> ActivitiesIds = activitiesFromTherapistService.SelectActivityIdCompletedMethod(userTAGId, completed);
            if (!ActivitiesIds.isEmpty()) {
                List<String> ActivitiesIdsString = new ArrayList<>();
                for (int ActivityId : ActivitiesIds) {
                    String id = String.valueOf(ActivityId);
                    if (id != null) {
                        ActivitiesIdsString.add(id);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(ActivitiesIdsString);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }
    @PostMapping("/userTAG/TherapistUsernamePendiente")
    public ResponseEntity TerapeutaPendiente(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 0;
        
        if(userTAGId > 0)
        {
            List<Integer> userTherapistIds =  activitiesFromTherapistService.SelectTherapistIdCompletedMethod(userTAGId, completed);

            if (!userTherapistIds.isEmpty()) {
                List<String> usernames = new ArrayList<>();
                for (int userTherapistId : userTherapistIds) {
                    int userIdT = userTherapistService.SearchIdByTherapistMethod(userTherapistId);
                    String usernameT = userService.SearchUsernameByIdMethod(userIdT);
                    if (usernameT != null) {
                        usernames.add(usernameT);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(usernames);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error usuario TAG");
        }
    }
    @PostMapping("/userTAG/TherapistUsernameCompletado")
    public ResponseEntity TerapeutaCompletado(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 1;
        
        if(userTAGId > 0)
        {
            List<Integer> userTherapistIds =  activitiesFromTherapistService.SelectTherapistIdCompletedMethod(userTAGId, completed);

            if (!userTherapistIds.isEmpty()) {
                List<String> usernames = new ArrayList<>();
                for (int userTherapistId : userTherapistIds) {
                    int userIdT = userTherapistService.SearchIdByTherapistMethod(userTherapistId);
                    String usernameT = userService.SearchUsernameByIdMethod(userIdT);
                    if (usernameT != null) {
                        usernames.add(usernameT);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(usernames);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error usuario TAG");
        }
    }
    @PostMapping("/userTAG/ViewActivityDatesPendiente")
    public ResponseEntity postMethodDatesPendiente(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 0;

        if(userTAGId > 0)
        {
            List<Date> DiaryDates =  activitiesFromTherapistService.SelectActivityDateCompletedMethod(userTAGId, completed);
            if (!DiaryDates.isEmpty()) {
                List<String> DiaryIdStr = new ArrayList<>();
                for (Date DiaryDate : DiaryDates) {
                    String date = String.valueOf(DiaryDate);
                    if (date != null) {
                        DiaryIdStr.add(date);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(DiaryIdStr);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
    @PostMapping("/userTAG/ViewActivityDatesCompletado")
    public ResponseEntity postMethodDatesCompletado(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int completed = 1;

        if(userTAGId > 0)
        {
            List<Date> DiaryDates =  activitiesFromTherapistService.SelectActivityDateCompletedMethod(userTAGId, completed);
            if (!DiaryDates.isEmpty()) {
                List<String> DiaryIdStr = new ArrayList<>();
                for (Date DiaryDate : DiaryDates) {
                    String date = String.valueOf(DiaryDate);
                    if (date != null) {
                        DiaryIdStr.add(date);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(DiaryIdStr);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay actividades");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
    @PostMapping("/userTAG/dateActivity")
    public ResponseEntity postMethodDate(@RequestParam("activityTId") String activityTIdstr){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        Date dateMax = activitiesFromTherapistService.SelectDateMaxActivityMethod(activityTId);

        if(dateMax != null)
        {
            String datestr = String.valueOf(dateMax);
            return ResponseEntity.status(HttpStatus.OK).body(datestr);
            
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/userTAG/labelActivity")
    public ResponseEntity postMethodLabel(@RequestParam("activityTId") String activityTIdstr){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }
        
        String label = activitiesFromTherapistService.SelectLabelActivityMethod(activityTId);

        if(label != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(label);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/userTAG/descriptionActivity")
    public ResponseEntity postMethodDescrip(@RequestParam("activityTId") String activityTIdstr){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }
        
        String descript = activitiesFromTherapistService.SelectDescriptionActivityMethod(activityTId);

        if(descript != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(descript);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/userTAG/documentActivity")
    public ResponseEntity postMethodDocument(@RequestParam("activityTId") String activityTIdstr){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }
        
        String document = activitiesFromTherapistService.SelectDocumentActivityMethod(activityTId);

        if(document != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(document);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/usersTAG/ActivitiesDocuments")
    public ResponseEntity postMethodName(@RequestParam("activityTId") String activityTIdstr,
                                        @RequestParam("referenceURL") String referenceURL) {

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int register = documentsService.RegisterNewActivityDocumentMethod(activityTId, referenceURL);

        if (register > 0) 
        {
            return ResponseEntity.status(HttpStatus.OK).body("success");
           
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @PostMapping("/userTAG/updateCompleted")
    public ResponseEntity<String> updateCompleted(@RequestParam("activityTId") String activityTIdstr){

        int activityTId;

        try {
            activityTId = Integer.parseInt(activityTIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int completed = 1;
        int updateActivity = activitiesFromTherapistService.UpdateCompletedMethod(activityTId, completed);

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
