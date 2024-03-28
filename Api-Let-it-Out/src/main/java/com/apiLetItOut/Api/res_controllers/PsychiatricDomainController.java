package com.apiLetItOut.Api.res_controllers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.PsychiatricDomainService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class PsychiatricDomainController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    PsychiatricDomainService psychiatricDomainService;

    @PostMapping("/userTAG/Psydomains")
    public ResponseEntity<String> RegisterNewDomains(@RequestParam("username") String username,
            @RequestParam("domainId") String domainIdstr,
            @RequestParam("score") String scorestr,
            @RequestParam("executionDate") String executionDatestr) throws ParseException {

        int domainId;
        int score;

        try {
            score = Integer.parseInt(scorestr);
            domainId = Integer.parseInt(domainIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date executionDate = formatoFecha.parse(executionDatestr);


        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {
            int psychiatricDomainQuestionaireId = psychiatricDomainService.RegisterNewDomainsMethod(userTAGId, domainId,
                    score, executionDate);
            if (psychiatricDomainQuestionaireId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar dominios");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }

    }

    @PostMapping("/userTAG/UpdatePsydomains")
    public ResponseEntity<String> UpdateDomains(@RequestParam("username") String username,
            @RequestParam("domainId") String domainIdstr,
            @RequestParam("score") String scorestr,
            @RequestParam("executionDate") String executionDatestr) throws ParseException {

        int domainId;
        int score;

        try {
            score = Integer.parseInt(scorestr);
            domainId = Integer.parseInt(domainIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date executionDate = formatoFecha.parse(executionDatestr);


        int userId = userService.SearchUserTAGMethod(username);
        userId = userService.SearchUsersByEmailMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {
            int psychiatricDomainQuestionaireId = psychiatricDomainService.UpdateDomainsMethod(userTAGId, domainId,
                    score, executionDate);
            if (psychiatricDomainQuestionaireId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar dominios");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }

    }
}
