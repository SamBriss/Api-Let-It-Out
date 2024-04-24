package com.apiLetItOut.Api.res_controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.FrequencyAlertsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class FrequencyAlertsControllers {
    @Autowired
    FrequencyAlertsService frequencyAlertsService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    UserService userService;
    
    @PostMapping("/userTAG/frequencyAlerts")
    public ResponseEntity RegisterNewFrequencyAlertsController(@RequestParam("usernameTAG") String usernameTAG,
                                                        @RequestParam("usernameTherapist") String usernameTherapist){

        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(usernameTAG);
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(usernameTherapist);

        if (userTAGId != null && userTherapistId != null) {
            int registro = frequencyAlertsService.RegisterNewFrequencyAlertsMethod(userTAGId, userTherapistId);
            if(registro > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("success");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no success");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    } 
    @PostMapping("/userTAG/selectFrequencyAlerts")
    public ResponseEntity SelectFAController(@RequestParam("username") String username){

        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(username);

        if (userTherapistId != null) {
            List<String> userTag = frequencyAlertsService.SelectfrecuencyAlerts(userTherapistId);
            if(!userTag.isEmpty())
            {
                Integer delete = frequencyAlertsService.DeleteVinculationMethod(userTherapistId);
                if(delete != null){
                    return ResponseEntity.status(HttpStatus.OK).body(userTag);
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("lista vacia");
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no success");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No tienes alertas");
        }
    }      
}
