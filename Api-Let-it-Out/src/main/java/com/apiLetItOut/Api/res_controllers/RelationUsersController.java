package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.RelationUsersService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class RelationUsersController {
    @Autowired
    RelationUsersService relationUsersService;

    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserTherapistService userTherapistService;

    @PostMapping("/users/AcceptRequest")
    public ResponseEntity postMethodName(@RequestParam("usernameTherapist") String usernameTherapist,
                                        @RequestParam("usernameTAG") String usernameTAG) {

        int userIdTTAG = userService.SearchUserTAGMethod(usernameTAG);
        int userTAGId = userTAGService.FindUserTAGMethod(userIdTTAG);

        int userIdTherapist = userService.SearchUserTAGMethod(usernameTherapist);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdTherapist);

        if (userTAGId > 0) 
        {
            int RegisterVinculation = relationUsersService.RegisterNewRelationUsersMethod(userTAGId, userTherapistId);
            
            if (RegisterVinculation > 0)
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

}
