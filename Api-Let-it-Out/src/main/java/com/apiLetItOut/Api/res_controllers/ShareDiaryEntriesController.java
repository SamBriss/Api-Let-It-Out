package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.ShareDiaryEntriesService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class ShareDiaryEntriesController {
    @Autowired
    ShareDiaryEntriesService shareDiaryEntriesService;
    @Autowired
    UserService userService;
    @Autowired
    UserTherapistService userTherapistService;
    @PostMapping("/userTAG/ShareDiaryEntry")
    public ResponseEntity postMethodNameSharw(@RequestParam("diaryId") String diaryIdstr,
                                             @RequestParam("username") String username) {
        int diaryId;

        try {
            diaryId = Integer.parseInt(diaryIdstr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FindUserTherapistsMethod(userId);

        int result = shareDiaryEntriesService.RegisterNewShareDiaryEntryMethod(diaryId, userTherapistId);

        if (result > 0)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("success");
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al compartir");
        }
    }
    
}
