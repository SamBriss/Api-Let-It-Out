package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AppointmentCalendarService;
import com.apiLetItOut.Api.services.CalendarConfigurationUsersService;
import com.apiLetItOut.Api.services.CalendarTAGActivityService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class CalendarTherapistApiController {
    @Autowired 
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    AppointmentCalendarService appointmentCalendarService;

    @Autowired
    CalendarTAGActivityService calendarTAGActivityService;
    
    @PostMapping("/userTherapistCalendar/GetAllAppointmentsMonth")
    public ResponseEntity showAllTAGCalendarAppointmentOfTheMonthh(@RequestParam("username") String username, 
                                                                  @RequestParam("dateReference") String dateReferenceStr,
                                                                  @RequestParam("monthPos") String monthPosStr) throws ParseException {
        Integer userId = null;
        userId = this.userService.SearchUserTAGMethod(username);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        if (userId != null && userId > 0) {
            System.out.println();
            System.out.println();
            System.out.println("mes esocigo:  " + monthPosStr);
            System.out.println("feecha escogidaa:  " + dateReferenceStr);
            int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
            System.out.println("userTherapistId:  " + userTherapistId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if (userTherapistId > 0) {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();

                if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound = appointmentCalendarService.findAllAppointmentsFromCalendarTherapistMethod(userTherapistId);
    
                    Date currentDate = new Date(); // Obtener la fecha actual
                    activitiesInCurrentMonth = new ArrayList<>();
    
                    // Filtrar los appointments que sean del mismo día o no hayan ocurrido
                    for (Object[] activity : listAllActivitiesFound) {
                        Date activityDate = (Date) activity[4];
                        if (isSameDay(activityDate, currentDate) || activityDate.after(currentDate)) {
                            activitiesInCurrentMonth.add(activity);
                        }
                    }

                } else if (!monthPosStr.equals("-1")) {
                    listAllActivitiesFound = appointmentCalendarService.findAllAppointmentsFromCalendarTherapistMethod(userTherapistId);
                    int referenceMonth = Integer.parseInt(monthPosStr);
                    for(int i =0; i < listAllActivitiesFound.size(); i++)
                    {
                        System.out.println("entra en month escogido foooor");
                        Date date = (Date) listAllActivitiesFound.get(i)[4];
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        int mes = calendar.get(Calendar.MONTH);
                        System.out.println("mes: "+mes);
                        
                        if (mes == referenceMonth) {
                            System.out.println("entra en que encuentra messs: "+mes+" = "+referenceMonth);
                            activitiesInCurrentMonth.add(listAllActivitiesFound.get(i));
                        }                
                    }

                } else if (!dateReferenceStr.equals("-1")) {
                    System.out.println("entra en fecha escogida");
                    Date dateReference = formatoFecha.parse(dateReferenceStr);
                    activitiesInCurrentMonth = appointmentCalendarService.findAppointmentsFromCalendarTherapistByDateMethod(userTherapistId, dateReference);
                }
    
                if (!activitiesInCurrentMonth.isEmpty()) {
                    return ResponseEntity.ok().body(activitiesInCurrentMonth);
                } else {
                    return ResponseEntity.ok().body("none");
                }
            }
            return ResponseEntity.ok().body("userTAGNotFound");
        }
        return ResponseEntity.ok().body("userTAGNotFound");
    }
   
    @PostMapping("/userTherapistCalendar/GetAllActivitiesMonth")
    public ResponseEntity showAllTAGCalendarActivitiesOfTheMonthh(@RequestParam("username") String username, 
                                                                  @RequestParam("dateReference") String dateReferenceStr,
                                                                  @RequestParam("monthPos") String monthPosStr) throws ParseException {
        
        System.out.println("entra en get activities therapist");
                                                                    Integer userId = null;
        userId = this.userService.SearchUserTAGMethod(username);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        if (userId != null && userId > 0) {
            System.out.println();
            System.out.println();
            System.out.println("mes esocigo:  " + monthPosStr);
            System.out.println("feecha escogidaa:  " + dateReferenceStr);
            int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
            System.out.println("userTherapistId:  " + userTherapistId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if (userTherapistId > 0) {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();

                if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesFromCalendarTherapistMethod(userTherapistId);
    
                    Date currentDate = new Date(); // Obtener la fecha actual
                    activitiesInCurrentMonth = new ArrayList<>();
                    System.out.println("current date: "+currentDate);
    
                    // Filtrar los appointments que sean del mismo día o no hayan ocurrido
                    activitiesInCurrentMonth = listAllActivitiesFound;
                    System.out.println(activitiesInCurrentMonth);

                } else if (!monthPosStr.equals("-1")) {
                    int referenceMonth = Integer.parseInt(monthPosStr);
                    referenceMonth++;
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesFromCalendarTherapistByMonthPosMethod(userTherapistId, referenceMonth); // esto está bn raro, no deberia de ser -1
                    activitiesInCurrentMonth = listAllActivitiesFound;
                     
                }

                else if (!dateReferenceStr.equals("-1")) {
                    System.out.println("entra en fecha escogida");
                    Date dateReference = formatoFecha.parse(dateReferenceStr);
                    activitiesInCurrentMonth = calendarTAGActivityService.findAllActivitiesFromCalendarTherapistByDateMethod(userTherapistId, dateReference);
                }
    
                if (!activitiesInCurrentMonth.isEmpty()) {
                    return ResponseEntity.ok().body(activitiesInCurrentMonth);
                } else {
                    return ResponseEntity.ok().body("none");
                }
            }
            return ResponseEntity.ok().body("userTAGNotFound");
        }
        return ResponseEntity.ok().body("userTAGNotFound");
    }
    
    @PostMapping("/userTherapistCalendar/GetAllExternalAppointmentsPatients")
    public ResponseEntity showAllTAGCalendarExternalAppointOfTheMonthh(@RequestParam("username") String username, 
                                                                  @RequestParam("dateReference") String dateReferenceStr,
                                                                  @RequestParam("monthPos") String monthPosStr) throws ParseException {
        
        System.out.println("entra en get activities therapist");
                                                                    Integer userId = null;
        userId = this.userService.SearchUserTAGMethod(username);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        if (userId != null && userId > 0) {
            System.out.println();
            System.out.println();
            System.out.println("mes esocigo:  " + monthPosStr);
            System.out.println("feecha escogidaa:  " + dateReferenceStr);
            int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
            System.out.println("userTherapistId:  " + userTherapistId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if (userTherapistId > 0) {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();

                if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesAppointmentsFromCalendarTherapistMethod(userTherapistId);
    
                    Date currentDate = new Date(); // Obtener la fecha actual
                    activitiesInCurrentMonth = new ArrayList<>();
                    System.out.println("current date: "+currentDate);
    
                    // Filtrar los appointments que sean del mismo día o no hayan ocurrido
                    activitiesInCurrentMonth = listAllActivitiesFound;
                    System.out.println(activitiesInCurrentMonth);

                } else if (!monthPosStr.equals("-1")) {
                    int referenceMonth = Integer.parseInt(monthPosStr);
                    referenceMonth++;
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesAppointmentsExternalFromCalendarTherapistByMonthPosMethod(userTherapistId, referenceMonth); // esto está bn raro, no deberia de ser -1
                    activitiesInCurrentMonth = listAllActivitiesFound;
                     
                }

                else if (!dateReferenceStr.equals("-1")) {
                    System.out.println("entra en fecha escogida");
                    Date dateReference = formatoFecha.parse(dateReferenceStr);
                    activitiesInCurrentMonth = calendarTAGActivityService.findAllActivitiesAppointmentsFromCalendarTherapistByDateMethod(userTherapistId, dateReference);
                }
    
                if (!activitiesInCurrentMonth.isEmpty()) {
                    return ResponseEntity.ok().body(activitiesInCurrentMonth);
                } else {
                    return ResponseEntity.ok().body("none");
                }
            }
            return ResponseEntity.ok().body("userTAGNotFound");
        }
        return ResponseEntity.ok().body("userTAGNotFound");
    }
    
    @PostMapping("/userTherapistCalendar/GetAllExternalAppointments")
    public ResponseEntity showAllTAGCalendarActivitiesToDoOfTheMonth(@RequestParam("username") String username, 
                                                                @RequestParam("dateReference") String dateReferenceStr,
                                                                @RequestParam("monthPos") String monthPosStr) throws ParseException
    {

            Integer userId = null;
            userId = this.userService.SearchUserTAGMethod(username);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            if (userId != null && userId > 0) {
                System.out.println();
                System.out.println();
                System.out.println("mes esocigo:  " + monthPosStr);
                System.out.println("feecha escogidaa:  " + dateReferenceStr);
                int userTherapistId = this.userTherapistService.FoundTherapistIdMethod(userId);
                System.out.println("userTherapistId:  " + userTherapistId);
                List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
                if (userTherapistId > 0) {
                    List<Object[]> listAllActivitiesFound = new ArrayList<>();
    
                    if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                        System.out.println("entra en ninguna fecha ni mes escogidas");
                        listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesAppointmentsFromCalendarTherapistMethod(userTherapistId);
        
                        Date currentDate = new Date(); // Obtener la fecha actual
                        activitiesInCurrentMonth = new ArrayList<>();
        
                        // Filtrar los appointments que sean del mismo día o no hayan ocurrido
                        for (Object[] activity : listAllActivitiesFound) {
                            Date activityDate = (Date) activity[4];
                            if (isSameDay(activityDate, currentDate) || activityDate.after(currentDate)) {
                                activitiesInCurrentMonth.add(activity);
                            }
                        }
    
                    } else if (!monthPosStr.equals("-1")) {
                        listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesAppointmentsFromCalendarTherapistMethod(userTherapistId);
                        int referenceMonth = Integer.parseInt(monthPosStr);
                        for(int i =0; i < listAllActivitiesFound.size(); i++)
                        {
                            System.out.println("entra en month escogido foooor");
                            Date date = (Date) listAllActivitiesFound.get(i)[4];
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
    
                            int mes = calendar.get(Calendar.MONTH);
                            System.out.println("mes: "+mes);
                            
                            if (mes == referenceMonth) {
                                System.out.println("entra en que encuentra messs: "+mes+" = "+referenceMonth);
                                activitiesInCurrentMonth.add(listAllActivitiesFound.get(i));
                            }                
                        }
    
                    } else if (!dateReferenceStr.equals("-1")) {
                        System.out.println("entra en fecha escogida");
                        Date dateReference = formatoFecha.parse(dateReferenceStr);
                        activitiesInCurrentMonth = calendarTAGActivityService.findAllActivitiesAppointmentsFromCalendarTherapistByDateMethod(userTherapistId, dateReference);
                    }
        
                    if (!activitiesInCurrentMonth.isEmpty()) {
                        return ResponseEntity.ok().body(activitiesInCurrentMonth);
                    } else {
                        return ResponseEntity.ok().body("none");
                    }
                }
                return ResponseEntity.ok().body("userTAGNotFound");
            }
            return ResponseEntity.ok().body("userTAGNotFound");
    
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
    
}
