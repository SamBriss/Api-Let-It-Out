package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ActivityTherapistCalendarService;
import com.apiLetItOut.Api.services.AppointmentCalendarService;
import com.apiLetItOut.Api.services.CalendarTAGActivityService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class CalendarTAGActivityApiController {
     @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired 
    CalendarTAGActivityService calendarTAGActivityService;

    @Autowired
    AppointmentCalendarService appointmentCalendarService;

    @PostMapping("/userTAGCalendar/addUserTAGActivityCalendar")
    public ResponseEntity<String> newCalendarConfiguration(@RequestParam("username") String username,
                                            @RequestParam("label") String label,
                                            @RequestParam("location") String location, 
                                            @RequestParam("direction") String direction,
                                            @RequestParam("date") String dateStr, 
                                            @RequestParam ("startHour") String startHourStr,
                                            @RequestParam("endHour") String endHourStr,
                                            @RequestParam("comments") String comments,
                                            @RequestParam("reminders") String remindersStr) {

        
          
      Integer userId = 0;
      userId = this.userService.SearchUserTAGMethod(username);
      if(userId!=0 && userId != null)
      {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startHour;
        java.util.Date endHour;
        int reminders;
        java.util.Date date, dateRegister;
        try {
            startHour = formatoHora.parse(startHourStr);
            endHour = formatoHora.parse(endHourStr);
            date = formatoFecha.parse(dateStr);
                
            Date dateTest = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(dateTest);
            dateRegister = formatoFecha.parse(formattedDate);

            reminders = Integer.parseInt(remindersStr);


            int userTAGId = this.userTAGService.FindUserTAGMethod(userId);
            //UsersTherapists userTherID = userTherapistService.FoundUserTherapistMethod(userId);
            System.out.println("TAGid:  "+userTAGId);
            if (userTAGId > 0) {
            int count = calendarTAGActivityService.SearchCountActivityUserTagCalendarMethod();
System.out.println("count of registers:   "+count);
                if(count > 0)
                {
                    // tabla de activitytherapistcalendar
                    java.util.List<Object[]> resultListActivitiesTherapist = calendarTAGActivityService.findRegistersOfUserTagActivitiesMethod(userTAGId, date);
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
                    
                    java.util.List<Object[]> resultListAppointmentCalendar = appointmentCalendarService.findRegistersOfUserTagAppointmentsMethod(userTAGId, date);
                    
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

                int result = calendarTAGActivityService.addNewActivityUserTagCalendarMethod(userTAGId, label, location, direction, date, startHour, endHour, dateRegister, comments, reminders);
                
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
