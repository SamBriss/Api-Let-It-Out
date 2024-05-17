package com.apiLetItOut.Api.res_controllers;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

@RestController
@RequestMapping("api")
public class RegisterTAGController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @PostMapping("/userTAG/register")
    public ResponseEntity<String> RegisterNewUserTAG(@RequestParam("levelTAGId") String levelTAGIdstr,
                                                    @RequestParam("medsExistence") String medsExistencestr,
                                                    @RequestParam("registerDate") String registerDatestr,
                                                    @RequestParam("levelTAGQuestionaireDate") String levelTAGQuestionaireDatestr,
                                                    @RequestParam("umbral") String umbrastrl,
                                                    @RequestParam("levelTechiniques") String levelTechiniquesstr,
                                                    @RequestParam("username") String username,
                                                    @RequestParam("password") String password,
                                                    @RequestParam("email") String email,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("lastNameP") String lastNameP,
                                                    @RequestParam("lastNameM") String lastNameM,
                                                    @RequestParam("tel") String tel,
                                                    @RequestParam("age") String ageStr,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("token") String token) throws ParseException {

        int levelTAGId;
        int medsExistence;
        int age;
        int umbral;
        int levelTechiniques;
        try {
            umbral =  Integer.parseInt(umbrastrl);
            levelTechiniques = Integer.parseInt(levelTechiniquesstr);
            medsExistence = Integer.parseInt(medsExistencestr);
            levelTAGId = Integer.parseInt(levelTAGIdstr);
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }

        // Formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        Date registerDate = formatoFecha.parse(registerDatestr);
        Date levelTAGQuestionaireDate = formatoFecha.parse(levelTAGQuestionaireDatestr);

        int countOfRegistrationUser = userService.RegisterNewUserMethod(username, password, email, name, lastNameP, lastNameM, tel, age, gender, token);
                                    
        if(countOfRegistrationUser>0)
        {
            int userId = userService.SearchUser(username, email);
            if (userId > 0){
                // Registro del terapeuta con la dirección y obtención del ID de usuario
                int userTAGId = userTAGService.RegisterNewUserTAGMethod(userId, levelTAGId, medsExistence, registerDate, levelTAGQuestionaireDate, umbral, levelTechiniques);
                if ( userTAGId > 0){
                    return ResponseEntity.status(HttpStatus.CREATED).body("success");
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario tag");
                }
            }
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario general");
            }
        }
        return null;
    }
    @PostMapping("/userTAG/dateLevelQuiz")
    public ResponseEntity postMethodNameVinculacion(@RequestParam("username") String username) {
        
        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);
        if(userTAGId!=null)
        {
            Date levelTAG = userTAGService.SelectDateLevelQuiz(userTAGId);
            if(levelTAG != null)
            {
                // Obtener la fecha actual
                Date fechaActual = new Date();
                
                // Calcular la fecha hace 6 meses
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaActual);
                calendar.add(Calendar.MONTH, -6);
                Date fechaHace6Meses = calendar.getTime();
                
                // Comparar las fechas
                if(levelTAG.after(fechaHace6Meses)) {
                    return ResponseEntity.ok().body("menor");
                } else {
                    return ResponseEntity.ok().body("mayor");
                }
            }
            else{

                return ResponseEntity.ok().body("no hay");
            }
        }
        return ResponseEntity.ok().body("No es TAG");
    }
    @PostMapping("/userTAG/updateLevel")
    public ResponseEntity<String> RegisterNewLevelId(@RequestParam("username") String username,
                                                    @RequestParam("levelTAGId") String levelTAGIdStr){

        int levelTAGId;
        try {
            levelTAGId = Integer.parseInt(levelTAGIdStr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }

        Integer userTAGId = userTAGService.GetUserTAGIdByeUsernameMethod(username);

        if(userTAGId > 0)
        {
            Integer update = userTAGService.UpdateUserTAGLevelTAGId(levelTAGId, userTAGId);
            
            if(update>0)
            {
                return ResponseEntity.ok().body("update");
            }
            else
            {
                return ResponseEntity.ok().body("no update");
            }
            
        }
        else
        {
            return ResponseEntity.ok().body("No hay TAG");
        }
    }
    
    @PostMapping("/userTAG/emergencyContacts")
    public ResponseEntity<String> methodEmergencyContacts(@RequestParam("username") String username, @RequestParam("name") String name, @RequestParam("tel") String tel)
    {
        Integer userId = userService.SearchUserTAGMethod(username);
        if(userId != null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId != null)
            {
                Integer res = userTAGService.InsertEmergencyContacts(userTAGId, name, tel);
                if(res >= 1){
                    return ResponseEntity.ok().body("success");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unsuccessful");
    }
}
