package com.apiLetItOut.Api.res_controllers;

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

import com.apiLetItOut.Api.services.CognitiveDistortionsService;
import com.apiLetItOut.Api.services.DictionaryCountService;
import com.apiLetItOut.Api.services.DictionaryWordsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class CognitiveDistortionsController {
    @Autowired
    CognitiveDistortionsService cognitiveDistortionsService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @Autowired
    DictionaryCountService dictionaryCountService;

    @Autowired
    DictionaryWordsService dictionaryWordsService;

    @Autowired
    UserTherapistService userTherapistService;

    @PostMapping("/userTAG/CognitiveDistortion")
    public ResponseEntity<String> RegisterNewDiaryEntries(@RequestParam("username") String username,
                                                    @RequestParam("dateSituation") String dateSituation,
                                                    @RequestParam("thought") String thought,
                                                    @RequestParam("physicalSensation") String physicalSensation,
                                                    @RequestParam("emotionalFeeling") String emotionalFeeling,
                                                    @RequestParam("consequence") String consequence,
                                                    @RequestParam("cognitiveDistortion") String cognitiveDistortion,
                                                    @RequestParam("usernameTherapist") String usernameTherapist){

        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        Integer userTherapistId;

        if (userTAGId > 0) {

            if(usernameTherapist == "no"){
                userTherapistId = null;
            }else{
                int userIdT = userService.SearchUserTAGMethod(usernameTherapist);
                userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdT);
            }

            int cognitiveDistortionReg = cognitiveDistortionsService.RegisterNewCognitiveDistortionMethod(dateSituation, thought, physicalSensation, emotionalFeeling, consequence, cognitiveDistortion, userTAGId, userTherapistId);

            if (cognitiveDistortionReg > 0){
                processEmotionalFeelingAndCount(userTAGId, emotionalFeeling);
                processPhysicalSensationAndCount(userTAGId, physicalSensation);
                processDateSituationAndCount(userTAGId, dateSituation);
                processThoughtsAndCount(userTAGId, thought);
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la herramienta");
            }
            
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    private void processThoughtsAndCount(int userTAGId, String thought) {
        
        // Limpieza y normalización
        String cleanedText = thought.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Procesar palabras de la categoría "11"
        List<String> words = Arrays.asList(cleanedText.split("\\s+"));
        for (String word : words) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(11, word);
            if (wordId != null) {
                processWord(userTAGId, wordId);
            }
        }
    }

    private void processDateSituationAndCount(int userTAGId, String dateSituation) {
        // Limpieza y normalización
        String cleanedText = dateSituation.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Procesar palabras de las categorías "1", "8" y "9"
        List<String> words = Arrays.asList(cleanedText.split("\\s+"));
        for (String word : words) {
            // Categorías a buscar
            List<Integer> categoryIds = Arrays.asList(1, 8, 9);
            for (Integer categoryId : categoryIds) {
                Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(categoryId, word);
                if (wordId != null) {
                    processWord(userTAGId, wordId);
                }
            }
        }
    }

    private void processPhysicalSensationAndCount(int userTAGId, String physicalSensation) {
        // Limpieza y normalización
        String cleanedText = physicalSensation.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Eliminar palabras de la categoría "13"
        List<String> words = Arrays.asList(cleanedText.split("\\s+"));
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedText = cleanedText.replace(word, "");
            }
        }

        // Procesar palabras de la categoría "14"
        Integer categoryId = 14; // ID de la categoría de sensaciones físicas
        for (String word : words) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWord(categoryId, word);
            if (wordId != null) {
                processWord(userTAGId, wordId);
            }
        }
    }

    private void processEmotionalFeelingAndCount(int userTAGId, String emotionalFeeling) {
        // Limpieza y normalización
        String cleanedEmotionalFeeling = emotionalFeeling.toLowerCase()
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("[.,]", ""); // Eliminar puntos y comas

        // Eliminar palabras de la categoría "13"
        String[] words = cleanedEmotionalFeeling.split("\\s+");
        for (String word : words) {
            if (dictionaryWordsService.countByCategoryIdAndWord(13, word) > 0) {
                cleanedEmotionalFeeling = cleanedEmotionalFeeling.replace(word, "");
            }
        }

        // Procesar palabras de las categorías "2" y "3"
        String[] emotionalWords = cleanedEmotionalFeeling.split("\\s+");
        for (String word : emotionalWords) {
            Integer wordId = dictionaryWordsService.findWordIdByCategoryAndWordInEmotionalCategories(word);
            if (wordId != null) {
                processWord(userTAGId, wordId);
            }
        }
    }

    private void processWord(int userTAGId, int wordId) {
        Integer wordCountId = dictionaryCountService.findCountIdByUserTagAndWordIdAndDateMethod(userTAGId, wordId, new Date());
        if (wordCountId == null) {
            dictionaryCountService.RegisterNewDictionaryCountMethod(userTAGId, wordId, 1, new Date(), 0);
        } else {
            dictionaryCountService.UpdateRepetitionsAndDateMethod(wordCountId);
        }
    }

    @PostMapping("/userTAG/QuantityCognitiveDistortionsSharedTherapist")
    public ResponseEntity postMethodNameCountByTherapist(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0)
        {
            int countRequest = cognitiveDistortionsService.countByUserTherapistIdCognitiveDistortionsSharedMethod(userTherapistId);

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

    @PostMapping("/userTAG/CognitiveDistortionsSharedTherapist")
    public ResponseEntity DatosDiarioParaTerapeuta(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0)
        {
            List<Object[]> informacion = cognitiveDistortionsService.findCognitiveDistortionsSharedMethod(userTherapistId);

            if (!informacion.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.OK).body(informacion);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay compartidos");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }
}
