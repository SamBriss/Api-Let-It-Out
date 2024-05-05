package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AttackMethodsService;
import com.apiLetItOut.Api.services.AttackRegisterDetailsService;
import com.apiLetItOut.Api.services.AttackRegistersService;
import com.apiLetItOut.Api.services.UserTAGService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/reportsOfAttack/")
public class ReportsOfAttacksRegisters {
    @Autowired
    UserTAGService userTAGService;
    
    @Autowired
    AttackRegistersService attackRegistersService;

    @Autowired
    AttackRegisterDetailsService attackRegisterDetailsService;

    @Autowired
    AttackMethodsService attackMethodsService;

    @PostMapping("getDataGeneral")
    public ResponseEntity<Map<String, Object>> GetDataOfRegisterAttack(@RequestParam("user") String username,
                                                                        @RequestParam("date") String dateStr,
                                                                        @RequestParam("startHour") String hours) {
        Map<String, Object> responseData = new HashMap<>();
        System.out.println(username);
        int userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        System.out.println(String.valueOf(userTAGId));
        LocalDate date = LocalDate.parse(dateStr);
        System.out.println(hours);
        String[] parts = hours.split("-");
        System.out.println(parts[0]);
        LocalTime startHour = LocalTime.parse(parts[0]);
        LocalDate weekBefore = date.minusDays(7);
        Integer attackRegisterId = attackRegistersService.SearchAttackIdForReportsMethod(date, startHour, userTAGId);
        if (attackRegisterId == null) {
            System.out.println("entro a null");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String place = attackRegisterDetailsService.SearchPlaceOfAttackMethod(attackRegisterId);
        String motive = attackRegisterDetailsService.SearchMotiveOfAttackMethod(attackRegisterId);
        String duration = attackRegistersService.SearchDurationByAttackIdMethod(attackRegisterId);
        Integer intensity = attackRegisterDetailsService.SearchIntensityOfAttackMethod(attackRegisterId);
        String emotions = attackRegisterDetailsService.SearchEmotionsOfAttackMethod(attackRegisterId);
        String physicalSensations = attackRegisterDetailsService.SearchPhysicalSensationsOfAttackMethod(attackRegisterId);
        String thoughts = attackRegisterDetailsService.SearchThoughtsOfAttackMethod(attackRegisterId);
        String typeOfthought = attackRegisterDetailsService.SearchtypeOfThoughtOfAttackMethod(attackRegisterId);
        String attackMethodName= attackRegisterDetailsService.SearchAttackMethodsOfAttackMethod(attackRegisterId);
        if (place == null || motive == null ||  intensity == null || emotions == null ||
                physicalSensations == null || thoughts == null || typeOfthought == null || attackMethodName == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        int countOfAttacksWeek = attackRegisterDetailsService.CountOfAttacksCompletedMethod(date, weekBefore, userTAGId);
        // Obtención de datos para las graficas
        // Intensidad
        List<Integer> intensitiesWeek = attackRegisterDetailsService.SearchIntensitiesMethod(date, weekBefore, userTAGId);
        // Duraciones
        List<String> durationsWeekLT = attackRegistersService.SearchDurations(date, weekBefore, userTAGId);
        List<Long> durationsWeek = new ArrayList<>();
        for (String duratio : durationsWeekLT) {
            LocalTime value = LocalTime.parse(duratio);
            int horas = value.getHour();
            int minutos = value.getMinute();
            int segundos = value.getSecond();
            int milisegundos = value.getNano() / 1000000; // Convertir nanosegundos a milisegundos
            long milisegundosTotales = horas * 3600000L + minutos * 60000L + segundos * 1000L + milisegundos;
            System.out.println("duracion es " + duratio + "mili " + milisegundosTotales);
            long segundosTotales = milisegundosTotales/1000;
            durationsWeek.add(segundosTotales);
        }
        // Tipos de pensamientos 
        Integer countOfHeterodirigidoThoughtWeek = attackRegisterDetailsService.SearchCountOfTypeOfThoughtsMethod(date, weekBefore, userTAGId, 'h');
        Integer countOfAutodirigidoThoughtWeek = attackRegisterDetailsService.SearchCountOfTypeOfThoughtsMethod(date, weekBefore, userTAGId, 'a');
        //  Metodos de afrontamiento
        Integer count1Week = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, weekBefore, userTAGId, 1);
        Integer count2Week = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, weekBefore, userTAGId, 2);
        Integer count3Week = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, weekBefore, userTAGId, 3);
        Integer count4Week = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, weekBefore, userTAGId, 4);
        Integer count5Week = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, weekBefore, userTAGId, 5);

        if(countOfAttacksWeek!=0 )
        {
            // 
            float percentageHWeek = (countOfHeterodirigidoThoughtWeek * 100f) / countOfAttacksWeek;
            float percentageAWeek = (countOfAutodirigidoThoughtWeek * 100f) / countOfAttacksWeek;

            float percentage1Week = (count1Week *100) / countOfAttacksWeek;
            float percentage2Week = (count2Week *100) / countOfAttacksWeek;
            float percentage3Week = (count3Week *100) / countOfAttacksWeek;
            float percentage4Week = (count4Week *100) / countOfAttacksWeek;
            float percentage5Week = (count5Week *100) / countOfAttacksWeek;
            // Datos generales
            String durationStr = String.valueOf(duration);
            responseData.put("place", place);
            responseData.put("duration", durationStr);
            responseData.put("motive", motive);
            responseData.put("intensity", intensity);
            responseData.put("emotions", emotions);
            responseData.put("physicalSensations", physicalSensations);
            responseData.put("thoughts", thoughts);
            responseData.put("typeOfthought", typeOfthought);
            responseData.put("method", attackMethodName);
            // Grafica intensidad
            responseData.put("intensitiesWeek", intensitiesWeek);
            // Grafica duración
            responseData.put("durationsWeek", durationsWeek);
            // Grafica tipos de pensamiento
            responseData.put("typeThoughtH", percentageHWeek);
            responseData.put("typeThoughtA", percentageAWeek);
            // Grafica metodos
            responseData.put("percentage1", percentage1Week);
            responseData.put("percentage2", percentage2Week);
            responseData.put("percentage3", percentage3Week);
            responseData.put("percentage4", percentage4Week);
            responseData.put("percentage5", percentage5Week);
        }
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("getDataDuration")
    public ResponseEntity<Map<String, Object>> GetDataDurationsGraphic(@RequestParam("user") String username, 
                                                                        @RequestParam("days") String daysStr,
                                                                        @RequestParam("date") String dateStr) 
    {
        Map<String, Object> responseData = new HashMap<>();
        System.out.println(username);
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);

        if(userTAGId!=null)
        {
            int days = Integer.parseInt(daysStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate beforeDate = date.minusDays(days);
            List<String> durationsLT= attackRegistersService.SearchDurations(date, beforeDate, userTAGId);
            List<Long> durations = new ArrayList<>();
            for (String duratio : durationsLT) {
                LocalTime value = LocalTime.parse(duratio);
                int horas = value.getHour();
                int minutos = value.getMinute();
                int segundos = value.getSecond();
                int milisegundos = value.getNano() / 1000000; // Convertir nanosegundos a milisegundos
                long milisegundosTotales = horas * 3600000L + minutos * 60000L + segundos * 1000L + milisegundos;
                System.out.println("duracion es " + duratio + "mili " + milisegundosTotales);
                long segundosTotales = milisegundosTotales/1000;
                durations.add(segundosTotales);
            }
            responseData.put("durations", durations);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("getDataIntensity")
    public ResponseEntity<Map<String, Object>> GetDataIntensitiesGraphic(@RequestParam("user") String username, 
                                                                        @RequestParam("days") String daysStr,
                                                                        @RequestParam("date") String dateStr) 
    {
        Map<String, Object> responseData = new HashMap<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        if(userTAGId!=null)
        {
            int days = Integer.parseInt(daysStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate beforeDate = date.minusDays(days);
            List<Integer> intensities = attackRegisterDetailsService.SearchIntensitiesMethod(date, beforeDate, userTAGId);
            responseData.put("intensities", intensities);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("getDataTypeThought")
    public ResponseEntity<Map<String, Object>> GetDataTypeThoughtGraphic(@RequestParam("user") String username, 
                                                                        @RequestParam("days") String daysStr,
                                                                        @RequestParam("date") String dateStr) 
    {
        Map<String, Object> responseData = new HashMap<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        if(userTAGId!=null)
        {
            int days = Integer.parseInt(daysStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate beforeDate = date.minusDays(days);
            int countOfAttacks = attackRegisterDetailsService.CountOfAttacksCompletedMethod(date, beforeDate, userTAGId);
            Integer countOfHeterodirigidoThought = attackRegisterDetailsService.SearchCountOfTypeOfThoughtsMethod(date, beforeDate, userTAGId, 'h');
            Integer countOfAutodirigidoThought = attackRegisterDetailsService.SearchCountOfTypeOfThoughtsMethod(date, beforeDate, userTAGId, 'a');
            int percentageH = (countOfHeterodirigidoThought * 100) / countOfAttacks;
            int percentageA = (countOfAutodirigidoThought * 100) / countOfAttacks;
            responseData.put("typeThoughtH", percentageH);
            responseData.put("typeThoughtA", percentageA);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("getDataMethod")
    public ResponseEntity<Map<String, Object>> GetDataMethodGraphic(@RequestParam("user") String userTAGIdStr, 
                                                                        @RequestParam("days") String daysStr,
                                                                        @RequestParam("date") String dateStr) 
    {
        Map<String, Object> responseData = new HashMap<>();
        Integer userTAGId=null;
        try{
            userTAGId = Integer.parseInt(userTAGIdStr);
        } catch(NumberFormatException ex)
        {}
        if(userTAGId!=null)
        {
            int days = Integer.parseInt(daysStr);
            LocalDate date = LocalDate.parse(dateStr);
            LocalDate beforeDate = date.minusDays(days);
            int countOfAttacks = attackRegisterDetailsService.CountOfAttacksCompletedMethod(date, beforeDate, userTAGId);
            Integer count1 = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, beforeDate, userTAGId, 1);
            Integer count2 = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, beforeDate, userTAGId, 2);
            Integer count3 = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, beforeDate, userTAGId, 3);
            Integer count4 = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, beforeDate, userTAGId, 4);
            Integer count5 = attackRegisterDetailsService.SearchCountOfMethodsMethod(date, beforeDate, userTAGId, 5);
            int percentage1 = (count1 * 100) / countOfAttacks;
            int percentage2 = (count2 * 100) / countOfAttacks;
            int percentage3 = (count3 * 100) / countOfAttacks;
            int percentage4 = (count4 * 100) / countOfAttacks;
            int percentage5 = (count5 * 100) / countOfAttacks;
            responseData.put("percentage1", percentage1);
            responseData.put("percentage2", percentage2);
            responseData.put("percentage3", percentage3);
            responseData.put("percentage4", percentage4);
            responseData.put("percentage5", percentage5);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    
}

