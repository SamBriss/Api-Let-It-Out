package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ActivityTherapistCalendarService;
import com.apiLetItOut.Api.services.AppointmentCalendarService;
import com.apiLetItOut.Api.services.CalendarConfigurationUsersService;
import com.apiLetItOut.Api.services.PreferenceDaysService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class AppointmentCalendarApiController {
     @Autowired
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired 
    ActivityTherapistCalendarService activityTherapistCalendarService;

    @Autowired
    PreferenceDaysService preferenceDaysService;
    @Autowired
    CalendarConfigurationUsersService calendarConfigurationUsersService;

    @Autowired
    AppointmentCalendarService appointmentCalendarService;

    @Autowired
    UserTAGService userTAGService;

    @PostMapping("/therapistCalendar/addAppointmentFromTherapist")
    public ResponseEntity<String> addAppointmentTherapist(@RequestParam("usernameTherapist") String username,
                                            @RequestParam("usernameTAG") String usernameTAG,
                                            @RequestParam ("startHour") String startHourStr,
                                            @RequestParam("endHour") String endHourStr) {

      Integer userId = 0;
      userId = this.userService.SearchUserTAGMethod(username); // find user therapist as his userId
      if(userId!=0 && userId != null)
      {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        //SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startHour;
        java.util.Date endHour;
        int therapistAcceptance, TAGacceptance, userTAGId;
        java.util.Date date;
        try {
            startHour = formatoHora.parse(startHourStr);
            endHour = formatoHora.parse(endHourStr);
            date = null;
            therapistAcceptance = 1;
            TAGacceptance = 0;

            int userIdTAG = userService.SearchUserTAGMethod(usernameTAG);
            userTAGId = userTAGService.FoundTAGMethod(userIdTAG);
            int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
            //UsersTherapists userTherID = userTherapistService.FoundUserTherapistMethod(userId);
            System.out.println("therapistID:  "+userTherapistId);
            if (userTherapistId > 0 && userTAGId > 0) {

                // agregar la actividad a la tabla de appointmentCalendar

                int result = appointmentCalendarService.addNewAppointmentFromTherapistCalendarMethod(userTAGId, userTherapistId, date, startHour, endHour, therapistAcceptance, TAGacceptance);
             
                return ResponseEntity.status(HttpStatus.OK).body(""+result);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
      }            
                                                
        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }

}
