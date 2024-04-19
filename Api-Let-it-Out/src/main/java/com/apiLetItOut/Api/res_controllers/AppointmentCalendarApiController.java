package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.models.MultipleDaysCalendarSettings;
import com.apiLetItOut.Api.services.ActivityTherapistCalendarService;
import com.apiLetItOut.Api.services.AppointmentCalendarService;
import com.apiLetItOut.Api.services.CalendarConfigurationUsersService;
import com.apiLetItOut.Api.services.CalendarTAGActivityService;
import com.apiLetItOut.Api.services.MultipleDaysCalendarSettingsService;
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

    @Autowired
    MultipleDaysCalendarSettingsService multipleDaysCalendarSettingsService;

    @Autowired
    CalendarTAGActivityService calendarTAGActivityService;

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

    @PostMapping("appointments/GetTAGAppointmentsNext14Days")
    public ResponseEntity postMethodName14Days(@RequestParam("username") String username) {
        
        java.util.List<Object[]> appointmentsNext14Days = appointmentCalendarService.findNext14DaysAppointmentsMethod(username);
        if(!appointmentsNext14Days.isEmpty())
        {
            java.util.List<Object[]> therapistInfoByAppointment = new ArrayList<>();
            for(int i = 0; i < appointmentsNext14Days.size(); i ++)
            {
                int appointmentId = Integer.parseInt(appointmentsNext14Days.get(i)[0].toString());
                Object[] d = userTherapistService.FindNameLastNamePUsernameTherapistByTherapistIdMethod(appointmentId);
                therapistInfoByAppointment.add(d);
            }
            
            java.util.List<Object[]> combinedList = combineLists(appointmentsNext14Days, therapistInfoByAppointment);
            // Mostramos el resultado
            for (Object[] array : combinedList) {
                System.out.print(array[0] +" , "+ array[1]+" , "+array[2]+" , "+array[3]+" , "+array[4]+" , ");
                Object[] objects = (Object[]) array[5];
                System.out.println(objects[0]+" , "+objects[1]+" , "+objects[2]);
            }
            return ResponseEntity.ok().body(combinedList);

        }
        return ResponseEntity.ok().body("none");
    }
    

    public static List<Object[]> combineLists(List<Object[]> list1, List<Object[]> list2) {
        List<Object[]> combinedList = new ArrayList<>();
        // Verificamos que las listas tengan el mismo tamaño
        if (list1.size() != list2.size()) {
            throw new IllegalArgumentException("Las listas no tienen el mismo tamaño");
        }
        // Recorremos ambas listas y combinamos los elementos en un nuevo arreglo
        for (int i = 0; i < list1.size(); i++) {
            Object[] array1 = list1.get(i);
            Object[] array2 = list2.get(i);
            // Creamos un nuevo arreglo combinado
            Object[] combinedArray = new Object[array1.length + array2.length];
            // Copiamos los elementos de list1 y list2 al nuevo arreglo combinado
            System.arraycopy(array1, 0, combinedArray, 0, array1.length);
            System.arraycopy(array2, 0, combinedArray, array1.length, array2.length); // Copiamos todos los elementos de list2
            // Agregamos el nuevo arreglo combinado a la lista resultante
            combinedList.add(combinedArray);
        }
        return combinedList;
    }

    @PostMapping("appointment/FindTherapistWeekDaysLabour")
    public ResponseEntity postMethodName(@RequestParam("username") String username) {
        try
        {
            if(username != null)
            {
                List<Integer> daysWork = multipleDaysCalendarSettingsService.findWeekDaysLabourTherapistMethod(username);
                if(!daysWork.isEmpty())
                {
                    return ResponseEntity.ok().body(daysWork);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok().body("none");
    }

    @PostMapping("appointments/getAllActivitiesExclusionAppointmentsInTherapist")
    public ResponseEntity postMethodName(@RequestParam("usernameTAG") String usernameTAG,
                                 @RequestParam("usernameTherapist") String usernameTherapist) {
        //TODO: process POST request
        List<Object[]> allEventsToExclude = appointmentCalendarService.findDateAndHoursOfAppointmentsTAGMethod(usernameTAG);
        List<Object[]> temporalList = appointmentCalendarService.findDateAndHoursOfAppointmentsTherapistMethod(usernameTherapist);
        if(!temporalList.isEmpty())
        {
            for(int i = 0; i < temporalList.size(); i++)
            {
                allEventsToExclude.add(temporalList.get(i));
            }
        }
        temporalList.clear();
        temporalList = activityTherapistCalendarService.findActivitiesDatesAndHourTherapistMethod(usernameTherapist);
        if(!temporalList.isEmpty())
        {
            for(int i = 0; i < temporalList.size(); i++)
            {
                allEventsToExclude.add(temporalList.get(i));
            }
        }

        temporalList.clear();
        temporalList = calendarTAGActivityService.findAllActivitiesTAGAfterTodayDatesAndHoursMethod(usernameTAG);
        if(!temporalList.isEmpty())
        {
            for(int i = 0; i < temporalList.size(); i++)
            {
                allEventsToExclude.add(temporalList.get(i));
            }
        }

        if(allEventsToExclude.isEmpty())
        {
            return ResponseEntity.ok().body("none");
        }
        
        return ResponseEntity.ok().body(allEventsToExclude);
    }
    
    @PostMapping("appointments/updateAppointment")
    public ResponseEntity postMethodUpdateAppointment(@RequestParam("appointmentId") String appointmentIdString,
                                                      @RequestParam("startHour") String startHourString,
                                                      @RequestParam("endHour") String endHouString,
                                                      @RequestParam("date") String dateString)
    {
        
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startHour;
        java.util.Date endHour;
        int therapistAcceptance, TAGacceptance, appointmentId;
        java.util.Date date;
        try {
            startHour = formatoHora.parse(startHourString);
            endHour = formatoHora.parse(endHouString);
            date = formatoFecha.parse(dateString);
            therapistAcceptance = 0;
            TAGacceptance = 1;
            appointmentId = Integer.parseInt(appointmentIdString);

            int result = appointmentCalendarService.UpdateAppointmentMethod(appointmentId, startHour, endHour, date, therapistAcceptance, TAGacceptance);
            return ResponseEntity.ok().body(result);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body("unsuccessful");
    }

    @PostMapping("appointment/createAppointmentChatBot")
    public ResponseEntity<String> addAppointmentChatBot(@RequestParam("usernameTAG") String usernameTAG,
                                                        @RequestParam("usernameTherapist") String usernameTherapist,
                                                        @RequestParam ("startHour") String startHourStr,
                                                        @RequestParam("endHour") String endHourStr,
                                                        @RequestParam("date") String dateStr) {

    Integer userId = 0;
    userId = this.userService.SearchUserTAGMethod(usernameTherapist); // find user therapist as his userId
    if(userId!=0 && userId != null)
    {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startHour;
        java.util.Date endHour;
        int therapistAcceptance, TAGacceptance, userTAGId;
        java.util.Date date;
        try {
            startHour = formatoHora.parse(startHourStr);
            endHour = formatoHora.parse(endHourStr);
            date = formatoFecha.parse(dateStr);
            therapistAcceptance = 0;
            TAGacceptance = 1;

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

    @PostMapping("appointments/GetTAGAppointmentsToConfirm")
    public ResponseEntity postMethodGetAppointmentsToConfirmTherapist(@RequestParam("username") String username) {
        
        java.util.List<Object[]> appointmentsToConfirm = appointmentCalendarService.findAppointmentsToConfirmByTherapistMethod(username);
        if(!appointmentsToConfirm.isEmpty())
        {
            java.util.List<Object[]> usersTherapistInfoByAppointment = new ArrayList<>();
            for(int i = 0; i < appointmentsToConfirm.size(); i ++)
            {
                int appointmentId = Integer.parseInt(appointmentsToConfirm.get(i)[0].toString());
                Object[] d = userTherapistService.FindNameLastNamePUsernameTherapistByTherapistIdMethod(appointmentId);
                usersTherapistInfoByAppointment.add(d);
            }
            
            java.util.List<Object[]> combinedList = combineLists(appointmentsToConfirm, usersTherapistInfoByAppointment);
            // Mostramos el resultado
            for (Object[] array : combinedList) {
                System.out.print(array[0] +" , "+ array[1]+" , "+array[2]+" , ");
                Object[] objects = (Object[]) array[3];
                System.out.println(objects[0]+" , "+objects[1]+" , "+objects[2]);
            }
            return ResponseEntity.ok().body(combinedList);

        }
        return ResponseEntity.ok().body("none");
    }

    @PostMapping("appointments/GetAppointmentsToAcceptOrDeclineTherapist")
    public ResponseEntity getAppointmentsAcceptOrDeclineTherapist(@RequestParam("username") String username) {
        //TODO: process POST request
        
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(username);
        if(String.valueOf(userTherapistId).contains("."))
        {
            userTherapistId = Integer.parseInt(String.valueOf(userTherapistId).substring(0, String.valueOf(userTherapistId).length() -2));
        }
        if(userTherapistId!=null)
        {
            List<Object[]> listAppointments = appointmentCalendarService.findAllAppointmentsToAcceptOrDeclineFromCalendarTherapistMethod(userTherapistId);
            if(!listAppointments.isEmpty())
            {
                return ResponseEntity.ok().body(listAppointments);
            }
        }
        return ResponseEntity.ok().body("none");
    }

    @PostMapping("appointments/updateTherapistAcceptance")
    public ResponseEntity updateTherapistAcceptance(@RequestParam("appointmentId") String appointmentIdStr,
                                                    @RequestParam("therapistAcceptance") String therapistAcceptanceStr) {
      
        try
        {
           int appointmentId = Integer.parseInt(appointmentIdStr);
           int therapistAcceptance = Integer.parseInt(therapistAcceptanceStr);
           int result = appointmentCalendarService.updateTherapistAcceptanceMethod(appointmentId, therapistAcceptance);
           return ResponseEntity.ok().body(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("none");
    }
    
    
}