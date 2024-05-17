package com.apiLetItOut.Api.res_controllers;

import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.PsychiatricDomainService;
import com.apiLetItOut.Api.services.RelationUsersService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api")
public class UpdateTAGProfile {
    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @Autowired
    RelationUsersService relationUserService;

    @Autowired
    PsychiatricDomainService psychiatricDomainService;

    @PostMapping("/userProfile/getDataGeneralUsername")
    public ResponseEntity<Map<String, Object>> getUserProfileGeneralByUsername(@RequestParam("username") String username) {
        Map<String, Object> responseData = new HashMap<>();
        Integer userId = userService.SearchUserTAGMethod(username);
        String email = userService.SearchEmailByUsernameMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        Integer levelTAG = userTAGService.SearchLevelTAGMethod(userTAGId);
        String tel = userService.SearchUserTelMethod(userId);
        String levelTAGStr="Leve";
        if(levelTAG!=null)
        {
            if(levelTAG==2)
            {
                levelTAGStr="Moderada";
            }
            else if(levelTAG==3)
            {
                levelTAGStr="Grave";
            }
            else if(levelTAG==4)
            {
                levelTAGStr="Severo";
            }
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("levelTAG", levelTAGStr);
        responseData.put("email", email);
        responseData.put("tel", tel);
        
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/getDataUsername")
    public ResponseEntity<Map<String, Object>> getUserProfileByUsername(@RequestParam("username") String username) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String tel = userService.SearchUserTelMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        String password = userService.SearchUserPasswordMethod(userId);
        int existenceRelation = relationUserService.ExistenceOfUserTAGWithTherapistMethod(userTAGId);
        System.out.println(existenceRelation);
        Integer levelTAG = userTAGService.SearchLevelTAGMethod(userTAGId);
        Boolean medsExistence = userTAGService.SearchMedsExistenceTAG(userTAGId);
        String levelTAGStr="Leve";
        if(levelTAG!=null)
        {
            if(levelTAG==2)
            {
                levelTAGStr="Moderada";
            }
            else if(levelTAG==3)
            {
                levelTAGStr="Grave";
            }
            else if(levelTAG==4)
            {
                levelTAGStr="Severo";
            }
        }
        
        if(gender.equals("F") || gender.equals("f"))
        {
            gender="Femenino";
        }
        else if(gender.equals("M") || gender.equals("m"))
        {
            gender="Masculino";
        }
        else if(gender.equals("P") || gender.equals("p"))
        {
            gender = "Prefiero no especificar";
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("tel", tel);
        responseData.put("gender", gender);
        responseData.put("password", password);
        responseData.put("levelTAG", levelTAGStr);
        responseData.put("medsExistence", medsExistence);
        responseData.put("relationCount", existenceRelation);
        
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/updateDataUsername")
    public ResponseEntity<String> updateProfileTAGByUsername(@RequestParam("name") String name,
                                                    @RequestParam("lastNameP") String lastNameP,
                                                    @RequestParam(name ="lastNameM", required = false) String lastNameM,
                                                    @RequestParam("age") String ageStr,
                                                    @RequestParam("tel") String tel,
                                                    @RequestParam("password") String password,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("username") String username,
                                                    @RequestParam("levelTAG") String levelTAGStr,
                                                    @RequestParam("medsExistence") String medsExistenceStr) {
        System.out.println(medsExistenceStr);
        int age, levelTAGId=1, medsExistence;
        try{
            age = Integer.parseInt(ageStr);
            medsExistence = Integer.parseInt(medsExistenceStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        if(levelTAGStr.equals("Leve"))
        {
            levelTAGId=1;
        } else if(levelTAGStr.equals("Moderada"))
        {
            levelTAGId=2;
        } else if (levelTAGStr.equals("Grave"))
        {
            levelTAGId=3;
        } else if(levelTAGStr.equals("Severo"))
        {
            levelTAGId=4;
        }
        char genderChar = gender.charAt(0);
        gender = String.valueOf(genderChar);
        String telCompleto = "+52" + tel;
        int umbral = 210 - age;
        int userId = userService.SearchUserTAGMethod(username);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        int countOfUpdatedUsers = userService.UpdateUsersMethod(userId, name, lastNameP,lastNameM, age, gender, telCompleto, password);
        
        if(countOfUpdatedUsers>0)
        {
            int countOfUpdatedTAG = userTAGService.UpdateUserTAGLevelMedsExistenceMethod(levelTAGId, medsExistence, userTAGId, umbral);
            if(countOfUpdatedTAG>0)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar TAG");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar usuario");
        }
    }

    @PostMapping("/userProfile/getDataGeneralEmail")
    public ResponseEntity<Map<String, Object>> getUserProfileGeneralByEmail(@RequestParam("email") String email) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUsersByEmailMethod(email);
        String username = userService.SearchUsernameByEmailMethod(email);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        Integer levelTAG = userTAGService.SearchLevelTAGMethod(userTAGId);
        String levelTAGStr="Leve";
        if(levelTAG!=null)
        {
            if(levelTAG==2)
            {
                levelTAGStr="Moderada";
            }
            else if(levelTAG==3)
            {
                levelTAGStr="Grave";
            }
            else if(levelTAG==4)
            {
                levelTAGStr="Severo";
            }
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("levelTAG", levelTAGStr);
        responseData.put("username", username);
        
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/getDataEmail")
    public ResponseEntity<Map<String, Object>> getUserProfileByEmail(@RequestParam("email") String email) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUsersByEmailMethod(email);
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String tel = userService.SearchUserTelMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        String password = userService.SearchUserPasswordMethod(userId);
        int existenceRelation = relationUserService.ExistenceOfUserTAGWithTherapistMethod(userTAGId);
        Integer levelTAG = userTAGService.SearchLevelTAGMethod(userTAGId);
        Boolean medsExistence = userTAGService.SearchMedsExistenceTAG(userTAGId);
        String levelTAGStr="Leve";
        if(levelTAG!=null)
        {
            if(levelTAG==2)
            {
                levelTAGStr="Moderada";
            }
            else if(levelTAG==3)
            {
                levelTAGStr="Grave";
            }
            else if(levelTAG==4)
            {
                levelTAGStr="Severo";
            }
        }
        
        if(gender.equals("F") || gender.equals("f"))
        {
            gender="Femenino";
        }
        else if(gender.equals("M") || gender.equals("m"))
        {
            gender="Masculino";
        }
        else if(gender.equals("P") || gender.equals("p"))
        {
            gender = "Prefiero no especificar";
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("tel", tel);
        responseData.put("gender", gender);
        responseData.put("password", password);
        responseData.put("levelTAG", levelTAGStr);
        responseData.put("medsExistence", medsExistence);
        responseData.put("relationCount", existenceRelation);
        
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/updateDataEmail")
    public ResponseEntity<String> updateProfileTAGByEmail(@RequestParam("name") String name,
                                                    @RequestParam("lastNameP") String lastNameP,
                                                    @RequestParam(name ="lastNameM", required = false) String lastNameM,
                                                    @RequestParam("age") String ageStr,
                                                    @RequestParam("tel") String tel,
                                                    @RequestParam("password") String password,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("email") String email,
                                                    @RequestParam("levelTAG") String levelTAGStr,
                                                    @RequestParam("medsExistence") String medsExistenceStr) {
        int age, levelTAGId=1, medsExistence;
        try{
            age = Integer.parseInt(ageStr);
            medsExistence = Integer.parseInt(medsExistenceStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        if(levelTAGStr.equals("Leve"))
        {
            levelTAGId=1;
        } else if(levelTAGStr.equals("Moderada"))
        {
            levelTAGId=2;
        } else if (levelTAGStr.equals("Grave"))
        {
            levelTAGId=3;
        } else if(levelTAGStr.equals("Severo"))
        {
            levelTAGId=4;
        }
        char genderChar = gender.charAt(0);
        gender = String.valueOf(genderChar);
        int umbral = 210-age;
        int userId = userService.SearchUsersByEmailMethod(email);
        int userTAGId = userTAGService.FoundTAGMethod(userId);
        int countOfUpdatedUsers = userService.UpdateUsersMethod(userId, name, lastNameP,lastNameM, age, gender, tel, password);
        
        if(countOfUpdatedUsers>0)
        {
            int countOfUpdatedTAG = userTAGService.UpdateUserTAGLevelMedsExistenceMethod(levelTAGId, medsExistence, userTAGId, umbral);
            if(countOfUpdatedTAG>0)
            {
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar TAG");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar usuario");
        }
    }

    @PostMapping("/userProfile/updateDomains")
    public ResponseEntity<String> updateDomains(@RequestParam("user") String user, 
                                            @RequestParam("domainId") String domainIdStr,
                                            @RequestParam("score") String scoreStr,
                                            @RequestParam("i") String iStr) {
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(user);
        if(userTAGId==null)
        {
            userTAGId = userTAGService.GetUserTAGIdByEmailMethod(user);
        }
        if(userTAGId!=null)
        {
            LocalDate executionDateLD = LocalDate.now();
            String executionDate = String.valueOf(executionDateLD);
            Integer i, score, domainId;
            try{
                i = Integer.parseInt(iStr);
                score = Integer.parseInt(scoreStr);
                domainId = Integer.parseInt(domainIdStr);
            }catch(NumberFormatException ex)
            {
                System.out.println("Los campos de numeros deben ser números enteros válidos");
                return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
            }
            List<Integer> domainsIds = psychiatricDomainService.SearchDomainsOfUserTAGMethod(userTAGId);
            int domainIdToReplace = domainsIds.get(i);
            int confirmation = psychiatricDomainService.UpdateDomainsNewMethod(userTAGId, domainId, score, executionDate, domainIdToReplace);
            if(confirmation>0){
                return ResponseEntity.status(HttpStatus.CREATED).body("success");
            }else{
                System.out.println("no se pudo cambiar el dominio");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar el dominio");
            }
        }
        else{
            System.out.println("no se pudo cambiar usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar usuario");
        }
    }
    
}