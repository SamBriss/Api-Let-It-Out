package com.apiLetItOut.Api.res_controllers;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGRequestService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class UserTAGRequestController {
    @Autowired
    UserTAGRequestService userTAGRequestService;

    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserTherapistService userTherapistService;

    @PostMapping("/userTAG/requestUserTAG")
    public ResponseEntity postMethodName(@RequestParam("username") String username,
                                        @RequestParam("status") String statusstr,
                                        @RequestParam("vinculationCode") String vinculationCodestr) {

        int status;
        int vinculationCode;

        try {
            status = Integer.parseInt(statusstr);
            vinculationCode = Integer.parseInt(vinculationCodestr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) 
        {
            int userTherapistId = userTherapistService.FindTherapistIdByCodeMethod(vinculationCode);
            
            if (userTherapistId > 0)
            {
                int Request = userTAGRequestService.RegisterRequestTAGMethod(userTAGId, status, userTherapistId);
                if (Request > 0) 
                {
                    return ResponseEntity.status(HttpStatus.OK).body("success");
                } 
                else 
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la solicitud");
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario Terapeuta");
            }
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    @PostMapping("/userTAG/ShowRequests")
    public ResponseEntity postMethodRquest(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);
        

        if(userTherapistId > 0)
        {
            List<Integer> userTAGIds =  userTAGRequestService.FoundUserTAGIdByTherapistMethod(userTherapistId);

            if (!userTAGIds.isEmpty()) {
                List<String> usernames = new ArrayList<>();
                for (int userTAGId : userTAGIds) {
                    int userIdByTAG = userTAGService.FoundIdByTAGMethod(userTAGId);
                    if (userIdByTAG > 0) {
                        String usernameTAG = userService.SearchUsernameByIdMethod(userIdByTAG);
                        if (usernameTAG != null) {
                            usernames.add(usernameTAG);
                        } else {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar en username");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario en users");
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(usernames);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTherapist/RequestQuantity")
    public ResponseEntity postMethodNameCount(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0)
        {
            int countRequest = userTAGRequestService.CountRequestQuantityMethod(userTherapistId);

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

    @PostMapping("/userTherapist/DenyRequest")
    public ResponseEntity postMethodNameDelete(@RequestParam("usernameTherapist") String usernameTherapist,
                                                @RequestParam("usernameTAG") String usernameTAG) {

        int userIdTherapist = userService.SearchUserTAGMethod(usernameTherapist);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdTherapist);

        if(userTherapistId > 0)
        {
            int userIdTAG = userService.SearchUserTAGMethod(usernameTAG);
            int userTAGId = userTAGService.FindUserTAGMethod(userIdTAG);

            if(userTAGId > 0)
            {
                int DeleteRequest = userTAGRequestService.DeleteRequestMethod(userTAGId, userTherapistId);

                if (DeleteRequest > 0)
                {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error no se borro");
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }    
}
