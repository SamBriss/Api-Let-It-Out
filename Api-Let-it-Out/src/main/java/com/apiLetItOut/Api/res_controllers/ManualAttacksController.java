package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ManualAttacksService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class ManualAttacksController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserTAGService userTAGService;

    @Autowired
    ManualAttacksService manualAttacksService;

    @PostMapping("/userTAG/ManualAttacks")
    public ResponseEntity<String> RegisterNewManualAttacksRegister(@RequestParam("username") String username,
                                                    @RequestParam("date") String datestr,
                                                    @RequestParam("hour") String hourstr,
                                                    @RequestParam("place") String place,
                                                    @RequestParam("motive") String motive,
                                                    @RequestParam("explanaitionResume") String explanaitionResume,
                                                    @RequestParam("intensity") String intensitystr,
                                                    @RequestParam("emotions") String emotions,
                                                    @RequestParam("physicalSensations") String physicalSensations,
                                                    @RequestParam("thoughts") String thoughts,
                                                    @RequestParam("typeOfThought") String typeOfThought,
                                                    @RequestParam("attackMethodsId") String attackMethodsIdstr) throws ParseException{

        int attackMethodsId;
        int intensity;        
        
        try {
            attackMethodsId = Integer.parseInt(attackMethodsIdstr);
            intensity = Integer.parseInt(intensitystr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date date = formatoFecha.parse(datestr);

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        // Convertir el String a Date
        Date hour = formatoHora.parse(hourstr);

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if ( userTAGId > 0){

            int manualAttackCout = manualAttacksService.RegisterNewManualAttackMethod(date, hour, 
            place, motive, place, intensity, emotions, physicalSensations, thoughts, typeOfThought, 
            attackMethodsId, userTAGId);

            if (manualAttackCout > 0)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar dominios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    } 
}
