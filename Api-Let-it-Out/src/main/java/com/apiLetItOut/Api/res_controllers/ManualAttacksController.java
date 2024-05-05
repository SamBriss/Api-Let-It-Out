package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.DictionaryCountService;
import com.apiLetItOut.Api.services.DictionaryWordsService;
import com.apiLetItOut.Api.services.ManualAttacksService;
import com.apiLetItOut.Api.services.TemporaryDictionaryService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class ManualAttacksController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    ManualAttacksService manualAttacksService;

    @Autowired
    DictionaryCountService dictionaryCountService;

    @Autowired
    DictionaryWordsService dictionaryWordsService;

    @Autowired
    TemporaryDictionaryService temporaryDictionaryService;

    @PostMapping("/userTAG/ManualAttacks")
    public ResponseEntity<String> RegisterNewManualAttacksRegister(@RequestParam("username") String username,
            @RequestParam("date") String datestr,
            @RequestParam("hour") String hourstr,
            @RequestParam("place") String place,
            @RequestParam("motive") String motive,
            @RequestParam("explanaitionResume") String explanaitionResume,
            @RequestParam("intensity") String intensitystr,
            @RequestParam("emotions") String emotions,
            @RequestParam("physicalSensations") String physicalSensations,
            @RequestParam("thoughts") String thoughts,
            @RequestParam("typeOfThought") String typeOfThought,
            @RequestParam("attackMethodsId") String attackMethodsIdstr) throws ParseException {

        int attackMethodsId;
        int intensity;

        try {
            attackMethodsId = Integer.parseInt(attackMethodsIdstr);
            intensity = Integer.parseInt(intensitystr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        // Convertir el String a Date
        Date date = formatoFecha.parse(datestr);

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        // Convertir el String a Date
        Date hour = formatoHora.parse(hourstr);

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);

        if (userTAGId > 0) {
            int manualAttackCout = 1;
            //manualAttackCout = manualAttacksService.RegisterNewManualAttackMethod(date, hour,
            //        place, motive, place, intensity, emotions, physicalSensations, thoughts, typeOfThought,
            //        attackMethodsId, userTAGId);

            if (manualAttackCout > 0) {
                processPlaceAndHourAndCount(userTAGId, place, hourstr, date);
                processMotiveAndCount(userTAGId, motive, date);
                processIntensityAndCount(userTAGId, intensity, date);
                processWordsInThoughts(userTAGId, thoughts, date);
                recognizeWordsInLocation(userTAGId, place);
                recognizeWordsInEmotions(userTAGId, emotions);
                recognizeWordsInStartHour(userTAGId, hourstr);
                recognizeWordsInThoughts(userTAGId, thoughts);
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar dominios");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
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

    private void recognizeWordsInThoughts(int userTAGId, String thoughts){
        // Limpieza y normalización de palabras en el campo "emotions"
        String cleanedThoughts = thoughts.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words13 = cleanedThoughts.split("\\s+");
        for (String word : words13) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedThoughts = cleanedThoughts.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "11"
        String[] words11 = cleanedThoughts.split("\\s+");
        for (String word : words11) {
            if (dictionaryWordsService.countByCategoryIdAndWord(11, word) > 0) {
                cleanedThoughts = cleanedThoughts.replace(word, "");
            }
        }
        
        System.out.println("palabras: " + cleanedThoughts);
        if(!cleanedThoughts.isEmpty()){
            String[] Newwords = cleanedThoughts.split("\\s+");
            for (String word : Newwords) {
                Integer tempWordId = temporaryDictionaryService.SelectTemporaryIdMethod(word, 11);
                System.out.println(tempWordId);
                if (tempWordId != null) {
                    temporaryDictionaryService.DeleteTemporaryWord(tempWordId);
                    dictionaryWordsService.registerNewWord(word, 11);
                } else {
                    temporaryDictionaryService.RegisterNewTemporaryWord(word, 11, 1);
                }
            }
        }
    }

    private void recognizeWordsInEmotions(int userTAGId, String emotions){
        // Limpieza y normalización de palabras en el campo "emotions"
        String cleanedEmotions = emotions.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas
    
        // Eliminar palabras de la categoría "13"
        String[] words13 = cleanedEmotions.split("\\s+");
        for (String word : words13) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedEmotions = cleanedEmotions.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "2"
        String[] words2 = cleanedEmotions.split("\\s+");
        for (String word : words2) {
            if (dictionaryWordsService.countByCategoryIdAndWord(2, word) > 0) {
                cleanedEmotions = cleanedEmotions.replace(word, "");
            }
        }

        // Eliminar palabras de la categoría "3"
        String[] words3 = cleanedEmotions.split("\\s+");
        for (String word : words3) {
            if (dictionaryWordsService.countByCategoryIdAndWord(3, word) > 0) {
                cleanedEmotions = cleanedEmotions.replace(word, "");
            }
        }
        
        System.out.println("palabras: " + cleanedEmotions);
        if(!cleanedEmotions.isEmpty()){
            String[] Newwords = cleanedEmotions.split("\\s+");
            for (String word : Newwords) {
                Integer tempWordId = temporaryDictionaryService.SelectTemporaryIdMethod(word, 3);
                System.out.println(tempWordId);
                if (tempWordId != null) {
                    temporaryDictionaryService.DeleteTemporaryWord(tempWordId);
                    dictionaryWordsService.registerNewWord(word, 3);
                } else {
                    temporaryDictionaryService.RegisterNewTemporaryWord(word, 3, 1);
                }
            }
        }
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

    private void processWordsInThoughts(int userTAGId, String thoughts, Date date) {
        // Limpieza y normalización de palabras en el campo "thoughts"
        String cleanedThoughts = thoughts.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Quitar puntos y comas

        // Eliminar palabras de la categoría "13"
        String[] words = cleanedThoughts.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedThoughts = cleanedThoughts.replace(word, "");
            }
        }

        // Procesar palabras de la categoría 11
        String[] remainingWords = cleanedThoughts.split("\\s+");
        for (String word : remainingWords) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(11, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
            }
        }
    }

    private void processIntensityAndCount(int userTAGId, int intensity, Date date) {
        // Procesar palabras de la categoría "7" en intensity
        Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(7, Integer.toString(intensity));
        if (wordId != null) {
            processWord(userTAGId, wordId, date);
        }
    }

    private void processMotiveAndCount(int userTAGId, String motive, Date date) {
        // Limpieza y normalización de motive
        String cleanedMotive = motive.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Eliminar palabras de la categoría "13"
        String[] words = cleanedMotive.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedMotive = cleanedMotive.replace(word, "");
            }
        }

        // Procesar palabras de las categorías "1", "8", "9" y "12" en motive
        String[] remainingWords = cleanedMotive.split("\\s+");
        for (String word : remainingWords) {
            List<Integer> categoryIds = Arrays.asList(1, 8, 9, 12);
            for (Integer categoryId : categoryIds) {
                Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(categoryId, word);
                if (wordId != null) {
                    processWord(userTAGId, wordId, date);
                }
            }
        }
    }

    private void processPlaceAndHourAndCount(int userTAGId, String place, String hour, Date date) {
        // Limpieza y normalización de place
        String cleanedPlace = place.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Eliminar palabras de la categoría "13" en place
        String[] words = cleanedPlace.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedPlace = cleanedPlace.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "1" en place
        String[] remainingPlaceWords = cleanedPlace.split("\\s+");
        for (String word : remainingPlaceWords) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(1, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
            }
        }

        // Limpieza y normalización de hour
        String cleanedHour = hour.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u');

        // Eliminar palabras de la categoría "13" en hour
        String[] hourWords = cleanedHour.split("\\s+");
        for (String word : hourWords) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedHour = cleanedHour.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "10" en hour
        String[] remainingHourWords = cleanedHour.split("\\s+");
        for (String word : remainingHourWords) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(10, word);
            if (wordId != null) {
                processWord(userTAGId, wordId, date);
            }
        }
    }

    private void processWord(int userTAGId, int wordId, Date date) {
        System.out.println(date);
        Integer wordCountId = dictionaryCountService.findCountIdByUserTagAndWordIdAndDateMethod(userTAGId, wordId, date);
        System.out.println(wordCountId);
        if (wordCountId == null) {
            dictionaryCountService.RegisterNewDictionaryCountMethod(userTAGId, wordId, 1, date, 1);
        } else {
            dictionaryCountService.UpdateRepetitionsAndDateMethod(wordCountId);
        }
    }
}
