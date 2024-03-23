package com.apiLetItOut.Api.res_controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.DirectionsService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class UpdateTherapistProfile {

    @Autowired
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    DirectionsService directionService;
    @PostMapping("/userProfile/getTDataGeneralUsername")
    public ResponseEntity<Map<String, Object>> getUserTProfileGeneralByUsername(@RequestParam("username") String username) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUserTAGMethod(username);
        String email = userService.SearchEmailByUsernameMethod(username);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        String street = directionService.SearchStreetMethod(directionId);
        String colony = directionService.SearchColonyMethod(directionId);
        int numExt = directionService.SearchNumExtMethod(directionId);

        String gendercom="Femenino";
        if(gender.equals("M"))
        {
            gendercom = "Masculino";
        }
        else if(gender.equals("P"))
        {
            gendercom = "Prefiero no especificar";
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("email", email);
        responseData.put("gender", gendercom);
        responseData.put("street", street);
        responseData.put("colony", colony);
        responseData.put("numExt", numExt);
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/getDataTUsername")
    public ResponseEntity<Map<String, Object>> getUserTProfileByUsername(@RequestParam("username") String username) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String tel = userService.SearchUserTelMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        String password = userService.SearchUserPasswordMethod(userId);
        String licence  = userTherapistService.SearchLicenceTherapistMethod(userTherapistId);
        String street = directionService.SearchStreetMethod(directionId);
        String colony = directionService.SearchColonyMethod(directionId);
        int numExt = directionService.SearchNumExtMethod(directionId);
        int numInt = directionService.SearchNumIntMethod(directionId);
        
        
        if(gender.equals("F") || gender.equals("f"))
        {
            gender="Femenino";
        }
        else if(gender.equals("M") || gender.equals("m"))
        {
            gender="Masculino";
        }
        else if(gender.equals("P") || gender.equals("P"))
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
        responseData.put("licence", licence);
        responseData.put("street", street);
        responseData.put("colony", colony);
        responseData.put("numExt", numExt);
        responseData.put("numInt", numInt);
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/updateDataTUsername")
    public ResponseEntity<String> updateProfileTAGByUsername(@RequestParam("name") String name,
                                                    @RequestParam("lastNameP") String lastNameP,
                                                    @RequestParam(name ="lastNameM", required = false) String lastNameM,
                                                    @RequestParam("age") String ageStr,
                                                    @RequestParam("tel") String tel,
                                                    @RequestParam("password") String password,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("username") String username,
                                                    @RequestParam("licence") String licence,
                                                    @RequestParam("street") String street,
                                                    @RequestParam("colony") String colony,
                                                    @RequestParam("numExt") String numExtStr,
                                                    @RequestParam(name = "numInt", required =false) String numIntStr) {
        int age, numExt, numInt;
        try{
            age = Integer.parseInt(ageStr);
            numExt =Integer.parseInt(numExtStr);
            numInt = Integer.parseInt(numIntStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        char genderChar = gender.charAt(0);
        gender = String.valueOf(genderChar);
        String telCompleto = "+521" + tel;
        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        int countOfUpdatedUsers = userService.UpdateUsersMethod(userId, name, lastNameP,lastNameM, age, gender, telCompleto, password);
        
        if(countOfUpdatedUsers>0)
        {
            int countOfDirectionUpdated = directionService.UpdateDirectionMethod(street, colony, numExt, numInt, directionId);
            if(countOfDirectionUpdated>0)
            {
                int countOfUpdateTherapist = userTherapistService.UpdateLicenceTherapistMethod(userTherapistId, licence);
                if(countOfUpdateTherapist > 0)
                {
                    return ResponseEntity.status(HttpStatus.CREATED).body("success");
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambia terapeuta");
                }
                
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar direccion");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar usuario");
        }
    }

    @PostMapping("/userProfile/getTDataGeneralEmail")
    public ResponseEntity<Map<String, Object>> getUserTProfileGeneralByEmail(@RequestParam("email") String email) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUsersByEmailMethod(email);
        String username = userService.SearchUsernameByEmailMethod(email);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        String street = directionService.SearchStreetMethod(directionId);
        String colony = directionService.SearchColonyMethod(directionId);
        int numExt = directionService.SearchNumExtMethod(directionId);

        String gendercom="Femenino";
        if(gender.equals("M"))
        {
            gendercom = "Masculino";
        }
        else if(gender.equals("P"))
        {
            gendercom = "Prefiero no especificar";
        }
        // Agregar los datos al mapa de respuesta
        responseData.put("name", name);
        responseData.put("lastNameP", lastNameP);
        responseData.put("lastNameM", lastNameM);
        responseData.put("age", age);
        responseData.put("username", username);
        responseData.put("gender", gendercom);
        responseData.put("street", street);
        responseData.put("colony", colony);
        responseData.put("numExt", numExt);
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/getDataTEmail")
    public ResponseEntity<Map<String, Object>> getUserTProfileByEmail(@RequestParam("email") String email) {
        Map<String, Object> responseData = new HashMap<>();
        int userId = userService.SearchUsersByEmailMethod(email);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        String name = userService.SearchNameMethod(userId);
        String lastNameP = userService.SearchUserLPMethod(userId);
        String lastNameM = userService.SearchUserLMMethod(userId);
        Integer age = userService.SearchUserAgeMethod(userId);
        String tel = userService.SearchUserTelMethod(userId);
        String gender = userService.SearchUserGenderMethod(userId);
        String password = userService.SearchUserPasswordMethod(userId);
        String licence  = userTherapistService.SearchLicenceTherapistMethod(userTherapistId);
        String street = directionService.SearchStreetMethod(directionId);
        String colony = directionService.SearchColonyMethod(directionId);
        int numExt = directionService.SearchNumExtMethod(directionId);
        int numInt = directionService.SearchNumIntMethod(directionId);
        
        
        if(gender.equals("F") || gender.equals("f"))
        {
            gender="Femenino";
        }
        else if(gender.equals("M") || gender.equals("m"))
        {
            gender="Masculino";
        }
        else if(gender.equals("P") || gender.equals("P"))
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
        responseData.put("licence", licence);
        responseData.put("street", street);
        responseData.put("colony", colony);
        responseData.put("numExt", numExt);
        responseData.put("numInt", numInt);
        // Retornar los datos como respuesta
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/userProfile/updateDataTEmail")
    public ResponseEntity<String> updateProfileTerapeutaByEmail(@RequestParam("name") String name,
                                                    @RequestParam("lastNameP") String lastNameP,
                                                    @RequestParam(name ="lastNameM", required = false) String lastNameM,
                                                    @RequestParam("age") String ageStr,
                                                    @RequestParam("tel") String tel,
                                                    @RequestParam("password") String password,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("email") String email,
                                                    @RequestParam("licence") String licence,
                                                    @RequestParam("street") String street,
                                                    @RequestParam("colony") String colony,
                                                    @RequestParam("numExt") String numExtStr,
                                                    @RequestParam(name = "numInt", required =false) String numIntStr) {
        int age, numExt, numInt;
        try{
            age = Integer.parseInt(ageStr);
            numExt =Integer.parseInt(numExtStr);
            numInt = Integer.parseInt(numIntStr);
        }catch(NumberFormatException e)
        {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        char genderChar = gender.charAt(0);
        gender = String.valueOf(genderChar);
        String telCompleto = "+521" + tel;
        int userId = userService.SearchUsersByEmailMethod(email);
        int userTherapistId = userTherapistService.SearchIdTherapistByUserIdMethod(userId);
        int directionId = userTherapistService.SearchDirectionIdTherapistMethod(userTherapistId);
        int countOfUpdatedUsers = userService.UpdateUsersMethod(userId, name, lastNameP,lastNameM, age, gender, telCompleto, password);
        
        if(countOfUpdatedUsers>0)
        {
            int countOfDirectionUpdated = directionService.UpdateDirectionMethod(street, colony, numExt, numInt, directionId);
            if(countOfDirectionUpdated>0)
            {
                int countOfUpdateTherapist = userTherapistService.UpdateLicenceTherapistMethod(userTherapistId, licence);
                if(countOfUpdateTherapist > 0)
                {
                    return ResponseEntity.status(HttpStatus.CREATED).body("success");
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambia terapeuta");
                }
                
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar direccion");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar usuario");
        }
    }

    
    
}
