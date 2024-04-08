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
import com.apiLetItOut.Api.services.MultipleDaysCalendarSettingsService;
import com.apiLetItOut.Api.services.PreferenceDaysService;
import com.apiLetItOut.Api.services.UserService;



@RestController
@RequestMapping("api")
public class CalendarConfigurationUsersApiController {
    @Autowired
    UserService userService;
    
    @Autowired
    PreferenceDaysService preferenceDaysService;

    @Autowired
    CalendarConfigurationUsersService calendarConfigurationUsersService;

    @Autowired
    MultipleDaysCalendarSettingsService multipleDaysCalendarSettings;

    @PostMapping("/config/addConfigurationFinal")
    public ResponseEntity<String> newCalendarConfiguration(@RequestParam("username") String username, 
                                            @RequestParam("startWorkDay") String startWorkDayStr, 
                                            @RequestParam ("endWorkDay") String endWorkDayStr) {
        
      System.out.println("llego aquuuuuiiiiii");                                         
        
      int userId = 0;
      userId = this.userService.SearchUserTAGMethod(username);
      if(userId!=0)
      {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        java.util.Date startWorkDay;
        java.util.Date endWorkDay;
        try {
            startWorkDay = formatoHora.parse(startWorkDayStr);
            endWorkDay = formatoHora.parse(endWorkDayStr);

            int result =  calendarConfigurationUsersService.RegisterNewCalendarConfigurationMethod(userId, startWorkDay, endWorkDay);
            if(result>0)
            {
                int configurationId = this.calendarConfigurationUsersService.SearchConfigurationIdByUserIdMethod(userId);
                return ResponseEntity.status(HttpStatus.OK).body(""+configurationId);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
      }
        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }
    
//D:\Valeria\Documents\.ceti\LetItOut\AppMovil\App_Git\Api Let it Out\Api-Let-it-Out
    @PostMapping("/preferenceDays/addPreferenceDays")
    public ResponseEntity<String> addPreferenceDays(@RequestParam("configurationId") String configurationIdStr,
                                            @RequestParam("weekDayId") String weekDayIdStr,
                                            @RequestParam("startHour") String StartHourStr,
                                            @RequestParam("endHour") String EndHourStr,
                                            @RequestParam("label") String label) {

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        try {
            Date EndHour = formatoHora.parse(EndHourStr);
            Date StartHour = formatoHora.parse(StartHourStr);
            int weekDayId = Integer.parseInt(weekDayIdStr);
            int configurationId = Integer.parseInt(configurationIdStr);

            int result = preferenceDaysService.insertPreferenceDays(configurationId, weekDayId, StartHour, EndHour, label);
            System.out.println("result:  "+result);

            if(result==0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(""+result);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }
    
    @PostMapping("/multPrefDays/addMultipleLabourWeekDays")
    public ResponseEntity<String> addMultipleLabourWeekDays(@RequestParam("configurationId") String configurationIdString,
                                            @RequestParam("weekDayId") String weekDayIdStr) {
        int configurationId, weekDayId;
        try{
            weekDayId = Integer.parseInt(weekDayIdStr);
            configurationId = Integer.parseInt(configurationIdString);
            
            int result = multipleDaysCalendarSettings.insertMultipleLabourWeekDaysMethod(configurationId, weekDayId);
            if(result>0)
            {
                return ResponseEntity.status(HttpStatus.OK).body(""+result);
            }
          

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }

    @PostMapping("/configCalendar/findExistenceOfConfigurationId")
    public ResponseEntity<String> postMethodName(@RequestParam("username") String username) {
        Integer userId = 0;
        userId = this.userService.SearchUserTAGMethod(username);
        if(userId!=null)
        {
            Integer configurationId = this.calendarConfigurationUsersService.SearchConfigurationIdByUserIdMethod(userId);
            if(configurationId!=null)
            {
            return ResponseEntity.status(HttpStatus.OK).body("1");
            }
            else
            {
                return ResponseEntity.ok("0");
            }
        }
        return ResponseEntity.ok("UserIdNotFound");
    }
    
    

}
