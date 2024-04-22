package com.apiLetItOut.Api.res_controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.FrecuencyGraphicsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class FrecuencyGraphicsController {
    @Autowired
    FrecuencyGraphicsService frecuencyGraphicsService;
    
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @PostMapping("/userTAG/frecuencyGraphics")
    public ResponseEntity SelectWeekController(@RequestParam("username") String username,
                                                @RequestParam("time") String time){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {

            switch (time) {
                case "week":
                    List<Object[]> week = frecuencyGraphicsService.SelectWeekMethod(userTAGId);

                    if (week != null){
                        return ResponseEntity.status(HttpStatus.CREATED).body(week);
                    }
                    else
                    {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sin resultados");
                    }

                case "month":
                    List<Object[]> month = frecuencyGraphicsService.SelectMonthMethod(userTAGId);

                    if (month != null){
                        return ResponseEntity.status(HttpStatus.CREATED).body(month);
                    }
                    else
                    {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sin resultados");
                    }
                case "sixMonths":
                    List<Object[]> six = frecuencyGraphicsService.SelectSixMonthsMethod(userTAGId);

                    if (six != null){
                        return ResponseEntity.status(HttpStatus.CREATED).body(six);
                    }
                    else
                    {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sin resultados");
                    }
                case "year":
                    List<Object[]> year = frecuencyGraphicsService.SelectYearMethod(userTAGId);

                    if (year != null){
                        return ResponseEntity.status(HttpStatus.CREATED).body(year);
                    }
                    else
                    {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sin resultados");
                    }
            
                default:
                
                    break;
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
        
        return null;
    }
    @PostMapping("/userTAG/increaseOrDecrease")
    public ResponseEntity increaseOrDecreaseController(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {

            String result = frecuencyGraphicsService.increaseOrDecreaseMethod(userTAGId);

            if(result != null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }
}
