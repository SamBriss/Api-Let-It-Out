package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class LogInApiController {
    @Autowired
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    UserTAGService userTAGService;

    @PostMapping("users/login/ByUsername")
    public ResponseEntity authenticateUserByUsername( @RequestParam(value = "username") String username,
                                                    @RequestParam("password") String password)
    {
       int userId = userService.SearchUserByUsernameMethod(username, password);

        if (userId > 0 ) 
        {
            Integer find = userTherapistService.FindUserTherapistsMethod(userId);

            if (find == 0)
            {
                find = userTAGService.FindUserTAGMethod(userId);
                return ResponseEntity.status(HttpStatus.OK).body("success UserTAG");
            }
            else 
            {
                return ResponseEntity.status(HttpStatus.OK).body("success UserTherapist");
            }

        }
        else  if (userId < 0)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no encontrado");
        }
        return null;
    }

    @PostMapping("users/login/ByEmail")
    public ResponseEntity authenticateUserByEmail(@RequestParam("username") String email,
                                            @RequestParam("password") String password)
    {
       int userId = userService.SearchUserByEmailMethod(email, password);

        if (userId > 0 ) 
        {
            int find = userTherapistService.FindUserTherapistsMethod(userId);

            if (find == 0)
            {
                find = userTAGService.FindUserTAGMethod(userId);
                return ResponseEntity.status(HttpStatus.OK).body("success UserTAG");
            }
            else 
            {
                return ResponseEntity.status(HttpStatus.OK).body("success UserTherapist");
            }
        }
        else  if (userId < 0)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no encontrado");
        }
        return null;
    }
    @PostMapping("users/login/getUsername")
    public ResponseEntity getUsername(@RequestParam("email") String email)
    {
       String username = userService.SearchUsernameByEmailMethod(email);

        if (username != null) 
        {
            return ResponseEntity.status(HttpStatus.OK).body(username);
            
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuario no encontrado");
        }
    }

    @PostMapping("users/login/alreadyhavetoken")
    public ResponseEntity alreadyhavetokenController()
    {
       Integer userId = userService.SelectAlreadyhavetoken();

        if (userId != null ) 
        {
            Integer find = userTherapistService.FindUserTherapistsMethod(userId);
            String username = userService.SearchUsernameByIdMethod(userId);

            StringBuilder builder = new StringBuilder();
            if (find == 0)
            {
                find = userTAGService.FindUserTAGMethod(userId);
                builder.append("TAG").append(",").append(username);
                return ResponseEntity.status(HttpStatus.OK).body(builder);
            }
            else 
            {
                builder.append("Terapeuta").append(",").append(username);
                return ResponseEntity.status(HttpStatus.OK).body(builder);
            }

        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("no hay token en uso");
        }
    }
}
