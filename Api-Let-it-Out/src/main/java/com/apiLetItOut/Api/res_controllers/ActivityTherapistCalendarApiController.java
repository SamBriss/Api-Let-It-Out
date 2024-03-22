package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.models.ActivityTherapistCalendar;
import com.apiLetItOut.Api.services.ActivityTherapistCalendarService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class ActivityTherapistCalendarApiController {
     @Autowired
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired 
    ActivityTherapistCalendarService activityTherapistCalendarService;

    @PostMapping("/therapistCalendar/addTherapistActivityCalendar")
    public ResponseEntity<String> newCalendarConfiguration(@RequestParam("username") String username, 
                                            @RequestParam("date") String dateStr, 
                                            @RequestParam ("startHour") String startHourStr,
                                            @RequestParam("endHour") String endHourStr,
                                            @RequestParam("motive") String motive,
                                            @RequestParam("appointment") String appointmeStr) {

        
          
      Integer userId = 0;
      userId = this.userService.SearchUserTAGMethod(username);
      if(userId!=0 && userId != null)
      {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startHour;
        java.util.Date endHour;
        int appointment;
        java.util.Date date;
        try {
            startHour = formatoHora.parse(startHourStr);
            endHour = formatoHora.parse(endHourStr);
            date = formatoFecha.parse(dateStr);
            appointment = Integer.parseInt(appointmeStr);


            int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
            //UsersTherapists userTherID = userTherapistService.FoundUserTherapistMethod(userId);
            System.out.println("therapistID:  "+userTherapistId);
            if (userTherapistId > 0) {

                int count = activityTherapistCalendarService.SearchCountActivityTherapistCalendarMethod();
System.out.println("count of registers:   "+count);
                if(count > 0)
                {
                    // tabla de activitytherapistcalendar
                    java.util.List<Object[]> resultListActivitiesTherapist = activityTherapistCalendarService.findRegistersOfTherapistActivitiesMethod(userTherapistId, date);
                    if(resultListActivitiesTherapist.size() > 0)
                    {
                        System.out.println("entro aquuuuuui");
                        for(int i =0; i < resultListActivitiesTherapist.size(); i++)
                        {
                            Date startHourr = (Date) resultListActivitiesTherapist.get(i)[0];
                            Date endHourr = (Date) resultListActivitiesTherapist.get(i)[1];

                            if(timeOverlaps(startHourr, endHourr, startHour, endHour))
                            {
                                return ResponseEntity.status(HttpStatus.OK).body("sobrepuesta");
                            }
                        }
                        
                    }

                    // tabla de appointments
                    
                    java.util.List<Object[]> resultListAppointmentCalendar = activityTherapistCalendarService.findRegistersOfTherapistActivitiesMethod(userTherapistId, date);
                    
                    if(resultListAppointmentCalendar.size() > 0)
                    {
                       for(int i =0; i < resultListAppointmentCalendar.size(); i++)
                        {
                            Date startHourr = (Date) resultListAppointmentCalendar.get(i)[0];
                            Date endHourr = (Date) resultListAppointmentCalendar.get(i)[1];

                            if(timeOverlaps(startHourr, endHourr, startHour, endHour))
                            {
                                return ResponseEntity.status(HttpStatus.OK).body("sobrepuesta");
                            }
                        }
                    }
                }
                // si llegó aqui es por que no está sobrepuesta
                // agregar la actividad a la tabla de activitytherapistcalendar

                int result = activityTherapistCalendarService.addNewActivityTherapistCalendarMethod(date, startHour, endHour, motive, appointment, userTherapistId);
                // Aquí guardas la nueva actividad en la base de datos
                // activityTherapistCalendarService.saveNewActivity(userTherapistId, date, startHour, endHour, motive, appointment);
                
                return ResponseEntity.status(HttpStatus.OK).body(""+result);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
      }            
                                                
        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }


    
    private boolean timeOverlaps(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }
}
