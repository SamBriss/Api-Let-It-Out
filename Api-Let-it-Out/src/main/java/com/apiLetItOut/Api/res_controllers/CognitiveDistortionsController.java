package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.CognitiveDistortionsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class CognitiveDistortionsController {
    @Autowired
    CognitiveDistortionsService cognitiveDistortionsService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @PostMapping("/userTAG/CognitiveDistortion")
    public ResponseEntity<String> RegisterNewDiaryEntries(@RequestParam("username") String username,
                                                    @RequestParam("dateSituation") String dateSituation,
                                                    @RequestParam("thought") String thought,
                                                    @RequestParam("physicalSensation") String physicalSensation,
                                                    @RequestParam("emotionalFeeling") String emotionalFeeling,
                                                    @RequestParam("consequence") String consequence,
                                                    @RequestParam("cognitiveDistortion") String cognitiveDistortion){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {

            int cognitiveDistortionReg = cognitiveDistortionsService.RegisterNewCognitiveDistortionMethod(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId);

            if (cognitiveDistortionReg > 0){
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la herramienta");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    
}
