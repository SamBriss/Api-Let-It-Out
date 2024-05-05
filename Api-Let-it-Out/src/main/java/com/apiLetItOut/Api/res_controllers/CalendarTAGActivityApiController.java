package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// este es el que me ha funcionado
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ActivitiesFromTherapistService;
import com.apiLetItOut.Api.services.AppointmentCalendarService;
import com.apiLetItOut.Api.services.CalendarTAGActivityService;
import com.apiLetItOut.Api.services.DictionaryCountService;
import com.apiLetItOut.Api.services.DictionaryWordsService;
import com.apiLetItOut.Api.services.TemporaryDictionaryService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;


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

    @Autowired
    ActivitiesFromTherapistService activitiesFromTherapistService;

    @Autowired
    DictionaryCountService dictionaryCountService;

    @Autowired
    DictionaryWordsService dictionaryWordsService;

    @Autowired
    TemporaryDictionaryService temporaryDictionaryService;

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

                int result;
                result = calendarTAGActivityService.addNewActivityUserTagCalendarMethod(userTAGId, label, location, direction, date, startHour, endHour, dateRegister, comments, reminders);
                processWordsInLocation(userTAGId, location);
                processWordsInDirection(userTAGId, direction);
                recognizeWordsInLocation(userTAGId, location);
                recognizeWordsInDirection(userTAGId, direction);
                recognizeWordsInStartHour(userTAGId, startHourStr);
                return ResponseEntity.status(HttpStatus.OK).body(""+result);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
      }            
                                                
        return ResponseEntity.status(HttpStatus.OK).body("unsuccesful");
    }

    private void recognizeWordsInLocation(int userTAGId, String location){
        // Limpieza y normalización de palabras en el campo "location"
        String cleanedLocation = location.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words = cleanedLocation.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedLocation = cleanedLocation.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "1"
        String[] words1 = cleanedLocation.split("\\s+");
        for (String word : words1) {
            if (dictionaryWordsService.countByCategoryIdAndWord(1, word) > 0) {
                cleanedLocation = cleanedLocation.replace(word, "");
            }
        }

        System.out.println("palabras: " + cleanedLocation);
        if(!cleanedLocation.isEmpty()){
            String[] Newwords = cleanedLocation.split("\\s+");
            for (String word : Newwords) {
                Integer tempWordId = temporaryDictionaryService.SelectTemporaryIdMethod(word, 1);
                System.out.println(tempWordId);
                if (tempWordId != null) {
                    temporaryDictionaryService.DeleteTemporaryWord(tempWordId);
                    dictionaryWordsService.registerNewWord(word, 1);
                } else {
                    temporaryDictionaryService.RegisterNewTemporaryWord(word, 1, 1);
                }
            }
        }
    }

    private void recognizeWordsInDirection(int userTAGId, String direction){
        // Limpieza y normalización de palabras en el campo "location"
        String cleanedDirection = direction.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("\\d", "") //Quitar numeros y secuencias
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words = cleanedDirection.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedDirection = cleanedDirection.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "1"
        String[] words1 = cleanedDirection.split("\\s+");
        for (String word : words1) {
            if (dictionaryWordsService.countByCategoryIdAndWord(1, word) > 0) {
                cleanedDirection = cleanedDirection.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "4"
        String[] words4 = cleanedDirection.split("\\s+");
        for (String word : words4) {
            if (dictionaryWordsService.countByCategoryIdAndWord(4, word) > 0) {
                cleanedDirection = cleanedDirection.replace(word, "");
            }
        }

        System.out.println("palabras: " + cleanedDirection);
        if(!cleanedDirection.isEmpty()){
            Integer tempWordId = temporaryDictionaryService.SelectTemporaryIdMethod(cleanedDirection, 4);
            System.out.println(tempWordId);
            if (tempWordId != null) {
                temporaryDictionaryService.DeleteTemporaryWord(tempWordId);
                dictionaryWordsService.registerNewWord(cleanedDirection, 4);
            } else {
                temporaryDictionaryService.RegisterNewTemporaryWord(cleanedDirection, 1, 1);
            }
            
        }
    }

    private void recognizeWordsInStartHour(int userTAGId, String startHour){
        if (dictionaryWordsService.countByCategoryIdAndWord(10, startHour) > 0) {
            startHour = "";
        }
        

        System.out.println("palabras: " + startHour);
        if(startHour != ""){
            Integer tempWordId = temporaryDictionaryService.SelectTemporaryIdMethod(startHour, 10);
            System.out.println(tempWordId);
            if (tempWordId != null) {
                temporaryDictionaryService.DeleteTemporaryWord(tempWordId);
                dictionaryWordsService.registerNewWord(startHour, 10);
            } else {
                temporaryDictionaryService.RegisterNewTemporaryWord(startHour, 10, 1);
            }
        }
    }

    private void processWordsInLocation(int userTAGId, String location) throws ParseException {
        // Limpieza y normalización de palabras en el campo "location"
        String cleanedLocation = location.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words = cleanedLocation.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedLocation = cleanedLocation.replace(word, "");
            }
        }
    
        // Procesar palabras de la categoría "1"
        String[] words1 = cleanedLocation.split("\\s+");
        for (String word : words1) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(1, word);
            if (wordId != null) {
                processWord(userTAGId, wordId);
                cleanedLocation = cleanedLocation.replace(word, "");
            }
        }
    }  

    private void processWordsInDirection(int userTAGId, String direction) throws ParseException {
        // Limpieza y normalización de palabras en el campo "location"
        String cleanedDirection = direction.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words = cleanedDirection.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedDirection = cleanedDirection.replace(word, "");
            }
        }
    
        // Procesar palabras de la categoría "4"
        String[] words4 = cleanedDirection.split("\\s+");
        for (String word : words4) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(4, word);
            if (wordId != null) {
                processWord(userTAGId, wordId);
                cleanedDirection = cleanedDirection.replace(word, "");
            }
        }
    } 
    
    private void processWord(int userTAGId, int wordId) throws ParseException {
        Date referenceDate = new Date();
        Calendar calendarReference = Calendar.getInstance();
        calendarReference.setTime(referenceDate);
        int referenceMonth = calendarReference.get(Calendar.MONTH);
        int referenceYear = calendarReference.get(Calendar.YEAR);
        int referenceDay = calendarReference.get(Calendar.DAY_OF_MONTH);
        String Month = String.valueOf(referenceMonth);
        String year = String.valueOf(referenceYear);
        String day = String.valueOf(referenceDay);
        String date = year + "-" + Month + "-" + day;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = formatoFecha.parse(date);
        System.out.println("reference: " + fecha);
        Integer wordCountId = dictionaryCountService.findCountIdByUserTagAndWordIdAndDateMethod(userTAGId, wordId, fecha);
        if (wordCountId == null) {
            dictionaryCountService.RegisterNewDictionaryCountMethod(userTAGId, wordId, 1, new Date(), 0);
        } else {
            dictionaryCountService.UpdateRepetitionsAndDateMethod(wordCountId);
        }
    }

    @PostMapping("/userTAGCalendar/GetAllAppointmentsMonth")
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
            int userTAGId = this.userTAGService.FindUserTAGMethod(userId);
            System.out.println("usertagid:  " + userTAGId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if (userTAGId > 0) {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();

                if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound = appointmentCalendarService.findAllAppointmentsFromCalendarTAGMethod(userTAGId);
    
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
                    listAllActivitiesFound = appointmentCalendarService.findAllAppointmentsFromCalendarTAGMethod(userTAGId);
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
                    activitiesInCurrentMonth = appointmentCalendarService.findAppointmentsFromCalendarTAGByDateMethod(userTAGId, dateReference);
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
   
    @PostMapping("/userTAGCalendar/GetAllActivitiesMonth")
    public ResponseEntity showAllTAGCalendarActivitiesOfTheMonthh(@RequestParam("username") String username, 
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
            int userTAGId = this.userTAGService.FindUserTAGMethod(userId);
            System.out.println("usertagid:  " + userTAGId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if (userTAGId > 0) {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();

                if (monthPosStr.equals("-1") && dateReferenceStr.equals("-1")) {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesFromCalendarTAGMethod(userTAGId);
    
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
                    listAllActivitiesFound = calendarTAGActivityService.findAllActivitiesFromCalendarTAGMethod(userTAGId);
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
                    activitiesInCurrentMonth = calendarTAGActivityService.findAllActivitiesFromCalendarTAGByDateMethod(userTAGId, dateReference);
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
    
    @PostMapping("/userTAGCalendar/GetAllActivitiesToDoMonth")
    public ResponseEntity showAllTAGCalendarActivitiesToDoOfTheMonth(@RequestParam("username") String username, 
                                                                @RequestParam("dateReference") String dateReferenceStr,
                                                                @RequestParam("monthPos") String monthPosStr) throws ParseException
    {
        Integer userId = null;
        userId = this.userService.SearchUserTAGMethod(username);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        if(userId != null && userId > 0)
        {
            System.out.println();
            System.out.println();
            System.out.println("mes esocigo:  "+monthPosStr);
            System.out.println("feecha escogidaa:  "+dateReferenceStr);
            int userTAGId = this.userTAGService.FindUserTAGMethod(userId);
            System.out.println("usertagid:  "+ userTAGId);
            List<Object[]> activitiesInCurrentMonth = new ArrayList<>();
            if(userTAGId > 0)
            {
                List<Object[]> listAllActivitiesFound = new ArrayList<>();
                if(monthPosStr.equals("-1") && dateReferenceStr.equals("-1"))
                {
                    System.out.println("entra en ninguna fecha ni mes escogidas");
                    listAllActivitiesFound =  activitiesFromTherapistService.findAllActivitiesToDoFromCalendarTAGMethod(userTAGId);
                }
                else if(!monthPosStr.equals("-1"))
                {
                    System.out.println("entra en mes escogido");
                    listAllActivitiesFound =  activitiesFromTherapistService.findAllActivitiesToDoFromCalendarTAGMethod(userTAGId); 
                }
                else if(!dateReferenceStr.equals("-1"))
                {
                    System.out.println("entra en fecha escogida");
                    Date dateReference = formatoFecha.parse(dateReferenceStr);
                    activitiesInCurrentMonth = activitiesFromTherapistService.findAllActivitiesToDoFromCalendarTAGByDateMethod(userTAGId, dateReference);
                }
                // u.activityId, u.label, u.location, u.direction, u.date, u.startHour, u.endHour, u.dateRegister, u.comments, u.reminders         
                            
                //System.out.println("list all act size:   "+listAllActivitiesFound.size());
                if(listAllActivitiesFound.size() > 0 && dateReferenceStr.equals("-1"))
                {
                    Date referenceDate = new Date();
                    Calendar calendarReference = Calendar.getInstance();
                    calendarReference.setTime(referenceDate);
                    int referenceMonth = calendarReference.get(Calendar.MONTH);
                    System.out.println("month reference:      "+referenceMonth);
                   for(int i =0; i < listAllActivitiesFound.size(); i++)
                    {
                        Date date = (Date) listAllActivitiesFound.get(i)[3];
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        int mes = calendar.get(Calendar.MONTH);
                        System.out.println("mes: "+mes);

                        if(monthPosStr!="-1")
                        {
                            if(Integer.parseInt(monthPosStr) == mes)
                            {
                                activitiesInCurrentMonth.add(listAllActivitiesFound.get(i));
                            }
                        }
                        else{
                            if (mes == referenceMonth) {
                                activitiesInCurrentMonth.add(listAllActivitiesFound.get(i));
                            }
                        }
                    
                    }
                    if(activitiesInCurrentMonth.size()>0)
                    {
                        return ResponseEntity.ok().body(activitiesInCurrentMonth);
                    }
                    else if(activitiesInCurrentMonth.isEmpty())
                    {
                        return ResponseEntity.ok().body("none");
                    }
                }
                else if(!dateReferenceStr.equals("-1"))
                {
                    
                    if(activitiesInCurrentMonth.size()>0)
                    {
                        return ResponseEntity.ok().body(activitiesInCurrentMonth);
                    }
                    else if(activitiesInCurrentMonth.isEmpty())
                    {
                        return ResponseEntity.ok().body("none");
                    }
                }
                else{
                    return ResponseEntity.ok().body("none");
                }
            }
            return ResponseEntity.ok().body("userTAGNotFound");
        }
        return ResponseEntity.ok().body("userTAGNotFound");
    }
    
    private boolean timeOverlaps(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
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
