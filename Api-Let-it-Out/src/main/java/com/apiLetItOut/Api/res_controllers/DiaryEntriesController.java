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

import com.apiLetItOut.Api.services.DiaryEntriesService;
import com.apiLetItOut.Api.services.EmotionsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class DiaryEntriesController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    EmotionsService emotionsService;

    @Autowired
    DiaryEntriesService diaryEntriesService;

    @PostMapping("/userTAG/DiaryEntries")
    public ResponseEntity<String> RegisterNewDiaryEntries(@RequestParam("username") String username,
                                                    @RequestParam("date") String datestr,
                                                    @RequestParam("hour") String hourstr,
                                                    @RequestParam("text") String text,
                                                    @RequestParam("emocion") String emotion) throws ParseException {

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date date = formatoFecha.parse(datestr);

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        // Convertir el String a Date
        Date hour = formatoHora.parse(hourstr);

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        int emotionId = emotionsService.SearchEmotionMethod(emotion);

        if (userTAGId > 0) {

            int entryDiary = diaryEntriesService.RegisterNewDiaryEntryMethod(date, hour, text, userTAGId, emotionId);

            if (entryDiary > 0){
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar entrada de diario");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }

    }
}
