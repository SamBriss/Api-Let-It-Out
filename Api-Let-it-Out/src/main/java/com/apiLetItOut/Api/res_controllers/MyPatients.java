package com.apiLetItOut.Api.res_controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AttackRegistersService;
import com.apiLetItOut.Api.services.DiaryEntriesService;
import com.apiLetItOut.Api.services.ListenedAudiosFeedbackService;
import com.apiLetItOut.Api.services.RelationUsersService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api/myPatients/")
public class MyPatients {
    @Autowired 
    UserTAGService userTAGService;
    @Autowired
    RelationUsersService relationUsersService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    ListenedAudiosFeedbackService listenedAudiosFeedbackService;

    @Autowired
    AttackRegistersService attackRegistersService;

    @Autowired
    DiaryEntriesService diaryEntriesService;

    @PostMapping("getListDataGeneral")
    public ResponseEntity<List<Object[]>> SearchPatientsDataGeneral(@RequestParam("user") String user)
    {
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(user);

        if(userTherapistId!=null)
        {
            List<Object[]> patientsList = relationUsersService.SearchDataOfPatientsMethod(userTherapistId);
            if(patientsList!=null)
            {
                return new ResponseEntity<>(patientsList, HttpStatus.OK);
            } 
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getTechniquesListened")
    public ResponseEntity<List<Object[]>> SearchAudiosIdListenedTAG(@RequestParam("user") String username)
    {
        int userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        List<Object[]> responseData = new ArrayList<>();
        responseData = listenedAudiosFeedbackService.SearchCountAverageOfTechnique(userTAGId);
        if(responseData!= null)
        {
            for (Object[] object : responseData) {
                System.out.println("count: " + object[0]);
                System.out.println("score: " + object[1]);
                System.out.println("url: " + object[2]);
            }
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getSharedDiaries")
    public ResponseEntity<List<Object[]>> SearchSharedDiariesEntries(@RequestParam("userTAG") String usernameTAG,
                                                                    @RequestParam("userT") String userT)
    {
        int userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(usernameTAG);
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(userT);
        if(userTherapistId==null)
        {
            userTherapistId = userTherapistService.findUserTherapistIdByEmailMethod(userT);
        }
        List<Object[]> responseData = new ArrayList<>();
        responseData = diaryEntriesService.SearchDiariesEntriesMethod(userTherapistId, userTAGId);
        if(responseData!= null)
        {
            for (Object[] object : responseData) {
                System.out.println("date: " + object[0]);
                System.out.println("hour: " + object[1]);
            }
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getRegistersAttack")
    public ResponseEntity<List<Object[]>> SearchAttacks(@RequestParam("user") String username)
    {
        Integer userTAGId=userTAGService.GetUserTAGIdByeUsernameMethod(username);
        List<Object[]> responseData = attackRegistersService.SearchAttacksOfUserMethod(userTAGId);
        if(responseData!=null)
        {
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("getDiary")
    public ResponseEntity<String> SearchText(@RequestParam("diaryId") String diaryIdStr)
    {
        Double diaryIdD = 0.0;
        int diaryId = 0;
        try
        {
            diaryIdD = Double.valueOf(diaryIdStr);
            diaryId = Integer.valueOf(diaryIdD.intValue());
        } catch(NumberFormatException ex)
        {

        }
        if(diaryId!=0)
        {
            String responseData = diaryEntriesService.SearchTextOfDiaryEntryMethod(diaryId);
            if(responseData!=null)
            {
                return new ResponseEntity<>(responseData, HttpStatus.OK);
            }
        }
        System.out.println(diaryId);
        System.out.println(diaryIdStr);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


}
