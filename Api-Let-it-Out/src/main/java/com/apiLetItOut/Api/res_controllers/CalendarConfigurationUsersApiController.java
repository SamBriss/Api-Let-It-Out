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

import com.apiLetItOut.Api.services.CalendarConfigurationUsersService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class CalendarConfigurationUsersApiController {
    @Autowired
    UserService userService;

    @Autowired
    CalendarConfigurationUsersService calendarConfigurationUsersService;

    
    @PostMapping("/config/addCalendarConfiguration")
    public ResponseEntity<String> RegisterNewDiaryEntries(@RequestParam("username") String username,
                                                    @RequestParam("startWorkDay") String startWorkDayStr,
                                                    @RequestParam("endWorkDay") String endWorkDayStr )throws ParseException {

        System.out.println("llega aquii");
                                                        
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("registro entrada de diario");
            
    }

}
