package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.PreferencesTAGUserService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class PreferencesTAGUserController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserTAGService userTAGService;

    @Autowired
    PreferencesTAGUserService preferencesTAGUserService;
    @PostMapping("/userTAG/PreferencesTAG")
    public ResponseEntity<String> RegisterNewPreferencesTAG(@RequestParam("username") String username,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("score") String scorestr,
                                                    @RequestParam("categoryId") String categoryIdstr){

        int score;
        int categoryId;        
        
        try {
            score = Integer.parseInt(scorestr);
            categoryId = Integer.parseInt(categoryIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if ( userTAGId > 0){
            int preferenceId = preferencesTAGUserService.PreferencesTAGUserMethod(name, score, userTAGId, categoryId);
            if (preferenceId > 0){
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
