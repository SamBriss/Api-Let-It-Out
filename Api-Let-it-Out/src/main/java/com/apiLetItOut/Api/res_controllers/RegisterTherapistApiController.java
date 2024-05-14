package com.apiLetItOut.Api.res_controllers;

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
public class RegisterTherapistApiController {
    @Autowired
    UserService userService;

    @Autowired
    UserTherapistService userTherapistService;

    @Autowired
    DirectionsService directionService; // Nuevo

    @PostMapping("userTherapist/register")
    public ResponseEntity<String> registerNewTherapistUser(@RequestParam("username") String username,
                                                            @RequestParam("password") String password,
                                                            @RequestParam("email") String email,
                                                            @RequestParam("name") String name,
                                                            @RequestParam("lastNameP") String lastNameP,
                                                            @RequestParam("lastNameM") String lastNameM,
                                                            @RequestParam("tel") String tel,
                                                            @RequestParam("age") String ageStr,
                                                            @RequestParam("gender") String gender,
                                                            @RequestParam("token") String tokenStr,
                                                            @RequestParam("licence") String licence,
                                                            @RequestParam("contract") String contractStr,
                                                            @RequestParam("vinculationCode") String vinculationCodeStr,
                                                            @RequestParam("street") String street,
                                                            @RequestParam("numExt") String numExtStr,
                                                            @RequestParam(value = "numInt", required = false) String numIntStr,
                                                            @RequestParam("colony") String colony) {
        // Validación de campos
        if (licence.isEmpty() || contractStr.isEmpty() || vinculationCodeStr.isEmpty() ||
            street.isEmpty() || colony.isEmpty() || username.isEmpty() || password.isEmpty() 
            || email.isEmpty() || name.isEmpty() || lastNameP.isEmpty() || lastNameM.isEmpty() 
            || tel.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || tokenStr.isEmpty()) {
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }
        // Convierte los string a los valores necesarios para poder usar el servicio para hacer registo a la bd
        int age;
        int numExt;
        int numInt;
        try {
            age = Integer.parseInt(ageStr);
            numExt = Integer.parseInt(numExtStr);
            numInt = Integer.parseInt(numIntStr);
        } catch (NumberFormatException e) {
            // Si ocurre un error al convertir las cadenas a enteros, devolver un error 400
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }
        boolean contract = Boolean.parseBoolean(contractStr);
        int vinculationCode = Integer.parseInt(vinculationCodeStr);
        // Register New User:
        int countOfRegistrationUser = userService.RegisterNewUserMethod(username, password,
                email, name, lastNameP, lastNameM, tel, age, gender, tokenStr);
        if(countOfRegistrationUser>0)
        {
            int userId = userService.SearchUser(username, email);
            // Registro de la dirección
            
            int countOfRegistrationDirection = directionService.RegisterNewDirectionsMethod(street, numExt, numInt, colony);
            if(countOfRegistrationDirection>0)
            {
                 // Registro del terapeuta con la dirección y obtención del ID de usuario
                int directionId = directionService.SearchDirectionIdMethod(street, numExt, numInt, colony);
                int userTherapistId = userTherapistService.RegisterNewUserTherapistMethod(userId, licence, contract, vinculationCode, directionId);

                if (userId > 0 && directionId>0 && userTherapistId>0) {
                    return ResponseEntity.status(HttpStatus.CREATED).body("success");
                } else  if (userId<0){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario general");
                } else if(directionId<0)
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar direccion");
                }
                else if(userTherapistId<0)
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario terapeuta");
                }
            }
           
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error general");
        
    }

    @PostMapping("/userTherapist/CodeDiponibility")
    public ResponseEntity<String> SearchTherapistCode(@RequestParam("vinculationCode") String vinculationCodestr){

        int vinculationCode;

        try {
            vinculationCode = Integer.parseInt(vinculationCodestr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int CountCode = userTherapistService.FindTherapistCodeMethod(vinculationCode);
        if(CountCode == 0){
            return ResponseEntity.status(HttpStatus.OK).body("Disponible");
        }
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No disponible");
        }
    }

    @PostMapping("/userTherapist/updateTherapist")
    public ResponseEntity<String> updateTherapistVinculationCode(@RequestParam("username") String username,
                                                                @RequestParam("vinculationCode") String vinculationCodestr){

        int vinculationCode;

        try {
            vinculationCode = Integer.parseInt(vinculationCodestr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0){
            int updateTherapistCode = userTherapistService.updateTherapistCodeMethod(userTherapistId, vinculationCode);

            if(updateTherapistCode > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("update");
            }
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No update");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No encontrado");
        } 
    }

    @PostMapping("/userTherapist/VerifyExistence")
    public ResponseEntity<String> VerifyExistenceCode(@RequestParam("vinculationCode") String vinculationCodestr){

        int vinculationCode;

        try {
            vinculationCode = Integer.parseInt(vinculationCodestr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int CountCode = userTherapistService.FindTherapistCodeMethod(vinculationCode);
        if(CountCode > 0){
            return ResponseEntity.status(HttpStatus.OK).body("existe");
        }
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No existe");
        }
    }

    @PostMapping("/userTherapist/alreadyCode")
    public ResponseEntity<String> VerifyTherapistExistenceCode(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0){
            int existanceCode = userTherapistService.SearchTherapistExistanceCodeMethod(userTherapistId);
            if (existanceCode > 0)
            {
                String codigo = String.valueOf(existanceCode);
                return ResponseEntity.status(HttpStatus.OK).body(codigo);
            }
            else if (existanceCode == 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("No tiene");
            }
        }
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No existe");
        }
        return null;
    }
}