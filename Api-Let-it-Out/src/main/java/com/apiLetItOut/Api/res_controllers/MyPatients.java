package com.apiLetItOut.Api.res_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("getChatsTherapist")
    public ResponseEntity<List<Object[]>> getUsersChat(@RequestParam("user") String userT)
    {
        List<Object[]> dataFull = new ArrayList<>();
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(userT);
        if(userTherapistId==null)
        {
            userTherapistId = userTherapistService.findUserTherapistIdByEmailMethod(userT);
        }
        if(userTherapistId!=null)
        {
            List<Object[]> myPatients = relationUsersService.SearchDataOfPatientsUsersTAGIdMethod(userTherapistId);
            for(Object[] data : myPatients)
            {
                if(data!=null)
                {
                    int userTAGId = (int) data[2];
                    Object[] objeto = {data[0], data[1], data[2], 1, "nada"};
                    dataFull.add(objeto);
                    List<Object[]> therapistRelatedPartial = relationUsersService.SearchTherapistRelatedTAG(userTAGId, userTherapistId);
                    for (Object[] objects : therapistRelatedPartial) {
                        if(objects!=null)
                        {
                            Object[] object2 = {objects[0], objects[1], objects[2], 2, data[3]};
                            dataFull.add(object2);
                        }
                        
                    } 
                }
                
            }
            
        }
        return new ResponseEntity<>(dataFull, HttpStatus.OK);
    }


    @PostMapping("getChatsTAG")
    public ResponseEntity<List<Object[]>> getUsersTAGChat(@RequestParam("user") String user)
    {
        List<Object[]> dataFull = new ArrayList<>();
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if(userTAGId==null)
        {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if(userTAGId!=null)
        {
            List<Object[]> myTherapists = relationUsersService.SearchDataOfTherapistTherapistIdMethod(userTAGId);
            for(Object[] data : myTherapists)
            {
                if(data!=null)
                {
                    Object[] objeto = {data[0], data[1], data[2], 1};
                    dataFull.add(objeto);
                }
                
            }
            
        }
        return new ResponseEntity<>(dataFull, HttpStatus.OK);
    }


    @PostMapping("getUsersSimilarGraphics")
    public ResponseEntity<Map<String, Object>> getUsersSimilarGraphics(@RequestParam("user") String user)
    {
        Map<String, Object> responseData = new HashMap<>();
        Integer userTherapistId = userTherapistService.findUserTherapistIdByUsernameMethod(user);
        if(userTherapistId==null)
        {
            userTherapistId = userTherapistService.findUserTherapistIdByEmailMethod(user);
        }
        if(userTherapistId!=null)
        {
            List<Object[]> patients = relationUsersService.SearchDataOfPatientsMethod(userTherapistId);
            //List<Date> allDates = new ArrayList<>();
            //allDates.add(2024-04-15);
            List<Integer> patientsLevel1 = relationUsersService.SearchPatientsLevelTAGMethod(userTherapistId, 1);
            List<Integer> patientsLevel2 = relationUsersService.SearchPatientsLevelTAGMethod(userTherapistId, 2);
            List<Integer> patientsLevel3 = relationUsersService.SearchPatientsLevelTAGMethod(userTherapistId, 3);
            List<Integer> patientsLevel4 = relationUsersService.SearchPatientsLevelTAGMethod(userTherapistId, 4);
            List<Integer> patientsMinors = relationUsersService.SearchPatientsAgeMethod(userTherapistId, 14, 7);
            List<Integer> patientsTeens = relationUsersService.SearchPatientsAgeMethod(userTherapistId, 21, 13);
            List<Integer> patientsYoungs = relationUsersService.SearchPatientsAgeMethod(userTherapistId, 36, 20);
            List<Integer> patientsAdults = relationUsersService.SearchPatientsAgeMethod(userTherapistId, 60, 35);
            List<Integer> patientsOlds = relationUsersService.SearchPatientsAgeMethod(userTherapistId, 100, 59);
            List<Integer> countUseButtonsLevelTAG = new ArrayList<>();
            List<Integer> levelsTAG= new ArrayList<>();
            levelsTAG.add(1);
            countUseButtonsLevelTAG.add(countUseButtons(patientsLevel1));
            levelsTAG.add(2);
            countUseButtonsLevelTAG.add(countUseButtons(patientsLevel2));
            levelsTAG.add(3);
            countUseButtonsLevelTAG.add(countUseButtons(patientsLevel3));
            levelsTAG.add(4);
            countUseButtonsLevelTAG.add(countUseButtons(patientsLevel4));
            List<Integer> countUseButtonsAge = new ArrayList<>();
            List<Integer> ages = new ArrayList<>();
            countUseButtonsAge.add(countUseButtons(patientsMinors));
            ages.add(10);
            countUseButtonsAge.add(countUseButtons(patientsTeens));
            ages.add(17);
            countUseButtonsAge.add(countUseButtons(patientsYoungs));
            ages.add(28);
            countUseButtonsAge.add(countUseButtons(patientsAdults));
            ages.add(47);
            countUseButtonsAge.add(countUseButtons(patientsOlds));
            ages.add(80);
            List<Integer> countUseDiaryLevelTAG = new ArrayList<>();
            countUseDiaryLevelTAG.add(countUseDiary(patientsLevel1));
            countUseDiaryLevelTAG.add(countUseDiary(patientsLevel2));
            countUseDiaryLevelTAG.add(countUseDiary(patientsLevel3));
            countUseDiaryLevelTAG.add(countUseDiary(patientsLevel4));
            List<Integer> countUseDiaryAge = new ArrayList<>();
            countUseDiaryAge.add(countUseDiary(patientsMinors));
            countUseDiaryAge.add(countUseDiary(patientsTeens));
            countUseDiaryAge.add(countUseDiary(patientsYoungs));
            countUseDiaryAge.add(countUseDiary(patientsAdults));
            countUseDiaryAge.add(countUseDiary(patientsOlds));
            List<Integer> avgScoreFeedbackLevel= new ArrayList<>();
            avgScoreFeedbackLevel.add(avgScoreFeedbacks(patientsLevel1));
            avgScoreFeedbackLevel.add(avgScoreFeedbacks(patientsLevel2));
            avgScoreFeedbackLevel.add(avgScoreFeedbacks(patientsLevel3));
            avgScoreFeedbackLevel.add(avgScoreFeedbacks(patientsLevel4));
            List<Integer> avgScoreFeedbackAge= new ArrayList<>();
            avgScoreFeedbackAge.add(avgScoreFeedbacks(patientsMinors));
            avgScoreFeedbackAge.add(avgScoreFeedbacks(patientsTeens));
            avgScoreFeedbackAge.add(avgScoreFeedbacks(patientsYoungs));
            avgScoreFeedbackAge.add(avgScoreFeedbacks(patientsAdults));
            avgScoreFeedbackAge.add(avgScoreFeedbacks(patientsOlds));
            responseData.put("size", patients.size());
            responseData.put("ages", ages);
            responseData.put("levelsTAG", levelsTAG);
            responseData.put("countUseButtonsLevelTAG", countUseButtonsLevelTAG);
            responseData.put("countUseButtonsAge", countUseButtonsAge);
            responseData.put("countUseDiaryLevelTAG", countUseDiaryLevelTAG);
            responseData.put("countUseDiaryAge", countUseDiaryAge);
            responseData.put("avgScoreFeedbackLevelTAG", avgScoreFeedbackLevel);
            responseData.put("avgScoreFeedbackAge", avgScoreFeedbackAge);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    public int avgScoreFeedbacks(List<Integer> patients)
    {
        int count = 0;
        for(Integer patient: patients)
        {
            Integer sum = relationUsersService.SearchCountUseButtonsByUserIdMethod(patient);
            if(sum!=null)
            {
                count += sum;
            }
        }
        if(patients.size()>0){
            return count / patients.size();
        }else{
            return 0;
        }
    }

    public int countUseButtons(List<Integer> patients)
    {
        int count = 0;
        for(Integer patient: patients)
        {
            Integer sum = relationUsersService.SearchCountUseButtonsByUserIdMethod(patient);
            if(sum!=null)
            {
                count += sum;
            }
        }
        return count;
    }

    public int countUseDiary(List<Integer> patients)
    {
        int count = 0;
        for(Integer patient: patients)
        {
            Integer sum = relationUsersService.SearchCountUseDiaryByUserIdMethod(patient);
            if(sum!=null)
            {
                count += sum;
            }
        }
        return count;
    }
}