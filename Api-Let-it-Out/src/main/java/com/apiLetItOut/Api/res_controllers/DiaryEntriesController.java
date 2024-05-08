package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.DiaryEntriesService;
import com.apiLetItOut.Api.services.DictionaryCountService;
import com.apiLetItOut.Api.services.DictionaryWordsService;
import com.apiLetItOut.Api.services.EmotionsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

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

    @Autowired
    DictionaryCountService dictionaryCountService;

    @Autowired
    DictionaryWordsService dictionaryWordsService;

    @Autowired
    UserTherapistService userTherapistService;

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
                processDiaryTextAndCount(userTAGId, text, date);
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

    private void processDiaryTextAndCount(int userTAGId, String text, Date date) {
        
        // Limpieza y normalización
        String cleanedText = text.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u');

        // Eliminar palabras de la categoría "13"
        String[] words = cleanedText.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedText = cleanedText.replace(word, "");
                System.out.println(cleanedText);
            }
        }

        // Procesar palabras de la categoría "1"
        String[] words1 = cleanedText.split("\\s+");
        for (String word : words1) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(1, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
                cleanedText = cleanedText.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "8"
        String[] words8 = cleanedText.split("\\s+");
        for (String word : words8) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(8, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
                cleanedText = cleanedText.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "9"
        String[] words9 = cleanedText.split("\\s+");
        for (String word : words9) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(9, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
                cleanedText = cleanedText.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "10"
        String[] words10 = cleanedText.split("\\s+");
        for (String word : words10) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(10, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
                cleanedText = cleanedText.replace(word, "");
            }
        }
    }

    private void processWord(int userTAGId, int wordId, Date date) {
        System.out.println(date);
        Integer wordCountId = dictionaryCountService.findCountIdByUserTagAndWordIdAndDateMethod(userTAGId, wordId, date);
        System.out.println(wordCountId);
        if (wordCountId == null) {
            dictionaryCountService.RegisterNewDictionaryCountMethod(userTAGId, wordId, 1, date, 0);
        } else {
            dictionaryCountService.UpdateRepetitionsAndDateMethod(wordCountId);
        }
    }

    @PostMapping("/userTAG/RequestQuantityDiary")
    public ResponseEntity postMethodNameCount(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            int countRequest = diaryEntriesService.CountRequestQuantityDiaryMethod(userTAGId);

            if (countRequest > 0)
            {
                String count = String.valueOf(countRequest);
                return ResponseEntity.status(HttpStatus.OK).body(count);
            }
            else if (countRequest == 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error numero no valido");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTAG/ViewDiaryIds")
    public ResponseEntity postMethodRquestIds(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            List<Integer> DiaryIds =  diaryEntriesService.SelectDiaryIdMethod(userTAGId);
            if (!DiaryIds.isEmpty()) {
                List<String> DiaryIdStr = new ArrayList<>();
                for (int DiaryId : DiaryIds) {
                    String id = String.valueOf(DiaryId);
                    if (id != null) {
                        DiaryIdStr.add(id);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al volver string");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(DiaryIdStr);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay diarios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTAG/ViewDiaryDates")
    public ResponseEntity postMethodDates(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            List<Date> DiaryDates =  diaryEntriesService.SelectDiaryDateMethod(userTAGId);
            if (!DiaryDates.isEmpty()) {
                List<String> DiaryIdStr = new ArrayList<>();
                for (Date DiaryDate : DiaryDates) {
                    String date = String.valueOf(DiaryDate);
                    if (date != null) {
                        DiaryIdStr.add(date);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al volver string");
                    }    
                }
                return ResponseEntity.status(HttpStatus.OK).body(DiaryIdStr);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay diarios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTAG/ViewDiaryTexts")
    public ResponseEntity postMethodTexts(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            List<String> DiaryText =  diaryEntriesService.SelectDiaryTextMethod(userTAGId);
            if (!DiaryText.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(DiaryText);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay diarios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }
    
    @PostMapping("/userTAG/ViewDiaryEmotions")
    public ResponseEntity postMethodEmotions(@RequestParam("username") String username){
        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if(userTAGId > 0)
        {
            List<Integer> DiaryEmotionsId =  diaryEntriesService.SelectDiaryEmotionsMethod(userTAGId);
            if (!DiaryEmotionsId.isEmpty()) {
                List<String> EmotionsName = new ArrayList<>();
                    for (Integer diaryId : DiaryEmotionsId) {
                        String emotions = emotionsService.SearchEmotionNameMethod(diaryId);
                        if (emotions != null) {
                            EmotionsName.add(emotions);
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(EmotionsName);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay diarios");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

}
