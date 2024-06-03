package com.apiLetItOut.Api.res_controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AlgorithmTriggerElementsService;
import com.apiLetItOut.Api.services.RemindersService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api/reminders/")
public class RemindersController {
    @Autowired
    RemindersService remindersService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    AlgorithmTriggerElementsService algorithmTriggerElementsService;

    @PostMapping("insertReminder")
    public ResponseEntity<String> saveReminders(@RequestParam("user") String user,
            @RequestParam("name") String name,
            @RequestParam("time") String timeStr,
            @RequestParam("reminderType") String reminderTypeStr,
            @RequestParam("weekdaysId") String weekdaysIdStr) {

        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if (userTAGId == null) {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if (userTAGId != null) {
            String[] weekdaysStr = weekdaysIdStr.split(":");
            List<Integer> weekDaysId = new ArrayList<>();
            Integer reminderType = 0;
            LocalTime time = LocalTime.of(6, 30, 0);
            try {
                reminderType = Integer.valueOf(reminderTypeStr);
                time = LocalTime.parse(timeStr);
                for (String weekday : weekdaysStr) {
                    weekDaysId.add(Integer.valueOf(weekday));
                }
            } catch (NumberFormatException ex) {
                System.out.println("error al convertir a integer");
            } catch (Exception ex) {
                System.out.println("error: " + ex.getMessage());
            }

            Integer result = remindersService.InsertIntoRemindersMethod(userTAGId, name, time, reminderType, 1);
            if (result != null) {
                Integer reminderId = remindersService.SearchReminderIdMethod(userTAGId, name, time);
                if (reminderId != null) {
                    Integer result2 = remindersService.InsertIntoRemindersWeekdaysMethod(reminderId, weekDaysId);
                    if (result2 != null) {
                        return ResponseEntity.status(HttpStatus.CREATED).body("success");
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error al registrar los dias del recordatorio. Intentelo nuevamente");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error al buscar el recordatorio. Intentelo nuevamente");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error al registrar el recordatorio general. Intentelo nuevamente");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error, no se encontro el usuario. Cierre sesión e intentelo nuevamente.");
        }
    }

    @PostMapping("getData")
    public ResponseEntity<List<Object[]>> getDataReminders(@RequestParam("user") String user) {
        List<Object[]> dataFull = new ArrayList<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if (userTAGId == null) {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if (userTAGId != null) {
            List<Object[]> reminders = remindersService.SelectRemindersAllDataMethod(userTAGId);
            if (reminders != null && !reminders.isEmpty()) {
                for (Object[] objects : reminders) {
                    int reminderId = (Integer) objects[3];
                    List<Integer> weekDays = remindersService.SelectDaysReminder(reminderId);
                    String weekDaysString = weekDays.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(":"));
                    if (objects.length > 4) {
                        objects[4] = weekDaysString;
                    } else {
                        // Si el array no tiene 5 elementos, crear un nuevo array con el tamaño adecuado
                        Object[] newObjects = Arrays.copyOf(objects, 5);
                        newObjects[4] = weekDaysString;
                        objects = newObjects;
                    }
                    dataFull.add(objects);
                }
                return new ResponseEntity<>(dataFull, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(dataFull, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getDataAll")
    public ResponseEntity<List<Object[]>> getDataAllReminders(@RequestParam("user") String user) {
        List<Object[]> dataFull = new ArrayList<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if (userTAGId == null) {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if (userTAGId != null) {
            List<Object[]> reminders = remindersService.SelectRemindersAllDataMethod(userTAGId);
            if (!reminders.isEmpty() && reminders != null) {
                return new ResponseEntity<>(reminders, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(dataFull, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("deleteReminder")
    public ResponseEntity<String> deleteReminder(@RequestParam("user") String user,
            @RequestParam("name") String name,
            @RequestParam("time") String timeStr) {
        try {
            // Intentar obtener el ID del usuario usando el username
            Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);

            // Si no se encuentra, intentar obtenerlo usando el email
            if (userTAGId == null) {
                userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
            }

            // Si se encuentra el ID del usuario
            if (userTAGId != null) {
                // Convertir la cadena de tiempo a LocalTime
                LocalTime time = LocalTime.parse(timeStr);

                // Buscar el ID del recordatorio
                Integer reminderId = remindersService.SearchReminderIdMethod(userTAGId, name, time);

                // Si se encuentra el ID del recordatorio
                if (reminderId != null) {
                    // Borrar el recordatorio de los días de la semana
                    remindersService.DeleteFromRemindersWeekDaysMethod(reminderId);

                    // Borrar el recordatorio
                    remindersService.DeleteFromRemindersMethod(reminderId);

                    // Devolver una respuesta de éxito
                    return ResponseEntity.status(HttpStatus.OK).body("Success");
                } else {
                    // Devolver una respuesta de error si no se encuentra el recordatorio
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error, no se encontró el recordatorio.");
                }
            } else {
                // Devolver una respuesta de error si no se encuentra el usuario
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error, no se encontró el usuario. Cierre sesión e inténtelo nuevamente.");
            }
        } catch (Exception e) {
            // Devolver una respuesta de error si hay un problema al analizar la hora
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

    @PostMapping("getReminderData")
    public ResponseEntity<List<Object[]>> updateStatus(@RequestParam("reminderId") String reminderIdStr) {
        List<Object[]> data = new ArrayList<>();
        try {
            Integer reminderId = (int) Math.round(Double.parseDouble(reminderIdStr));
            List<Object[]> reminderData = remindersService.SelectReminderData(reminderId);
            List<Integer> weekDays = remindersService.SelectDaysReminder(reminderId);
            String weekDaysString = weekDays.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(":"));

            // Crear una nueva lista para almacenar los datos extendidos
            List<Object[]> extendedData = new ArrayList<>();
            for (Object[] originalObject : reminderData) {
                // Crear un nuevo objeto con un tamaño extendido
                Object[] extendedObject = Arrays.copyOf(originalObject, originalObject.length + 1);
                // Agregar weekDaysString en la nueva posición
                extendedObject[originalObject.length ] = weekDaysString;
                // Agregar el objeto extendido a la nueva lista
                extendedData.add(extendedObject);
            }

            return new ResponseEntity<>(extendedData, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            // Manejar la excepción de formato de número
            ex.printStackTrace();
        }
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("updateReminder")
    public ResponseEntity<String> updateStatus(@RequestParam("reminderId") String reminderIdStr,
            @RequestParam("name") String name,
            @RequestParam("reminderTypeId") String reminderTypeStr,
            @RequestParam("time") String timeStr,
            @RequestParam("weekDaysId") String weekdaysIdStr) {
        Integer reminderId = 0, reminderTypeId = 0;
        LocalTime time = LocalTime.of(6, 30, 0);
        String[] weekdaysStr = weekdaysIdStr.split(":");
        List<Integer> weekDaysId = new ArrayList<>();
        try {
            time = LocalTime.parse(timeStr);
            reminderId = (int) Math.round(Double.parseDouble(reminderIdStr));
            reminderTypeId = Integer.parseInt(reminderTypeStr);
            for (String weekday : weekdaysStr) {
                weekDaysId.add(Integer.valueOf(weekday));
            }
        } catch (NumberFormatException ex) {

        }
        if (reminderId > 0 && reminderTypeId > 0) {
            int count = remindersService.UpdateReminderData(reminderId, name, reminderTypeId, time);
            int count2 = remindersService.DeleteFromRemindersWeekDaysMethod(reminderId);
            if (count > 0 && count2 > 0) {
                Integer result2 = remindersService.InsertIntoRemindersWeekdaysMethod(reminderId, weekDaysId);
                if (result2 > 0) {
                    return ResponseEntity.status(HttpStatus.CREATED).body("success");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("bad");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("bad");
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("bad");
        }
    }
    /*
     * @PostMapping("updateAxietyAlert")
     * public ResponseEntity<String> updateStatus(@RequestParam("user") String
     * user, @RequestParam("type") String typeStr)
     * {
     * Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
     * if(userTAGId == null)
     * {
     * userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
     * }
     * if(userTAGId!=null)
     * {
     * 
     * }
     * }
     */
}