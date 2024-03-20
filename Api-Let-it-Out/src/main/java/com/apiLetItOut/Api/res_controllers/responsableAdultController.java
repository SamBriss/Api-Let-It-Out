package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ResponsableAdultService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class responsableAdultController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    ResponsableAdultService responsableAdultService;

    @PostMapping("/userTAG/ResponsableAdult")
    public ResponseEntity<String> RegisterResponsableAdult(@RequestParam("username") String username,
                                                    @RequestParam("nameResponsable") String nameResponsable,
                                                    @RequestParam("parentesco") String parentesco) {

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {
            int adultId = responsableAdultService.responsableAdultServiceMethod(userTAGId, nameResponsable, parentesco);

            if (adultId > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar dominios");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }

    }

}
