package com.apiLetItOut.Api.res_controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.UserService;

@RestController
@RequestMapping("api")
public class RegisterApiController {

    @Autowired
    UserService userService;

    //Aqui es donde recibe los datos de la solicitud
    @PostMapping("/usersGeneral/register")
    //aqui se puede generar el token
    public ResponseEntity<String> registerNewUser(@RequestParam("username") String username,
                                                  @RequestParam("password") String password,
                                                  @RequestParam("email") String email,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("lastNameP") String lastNameP,
                                                  @RequestParam("lastNameM") String lastNameM,
                                                  @RequestParam("tel") String tel,
                                                  @RequestParam("age") String ageStr,
                                                  @RequestParam("gender") String gender,
                                                  @RequestParam("token") String tokenStr){
        // Verificaciones de campos vacios
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty() || lastNameP.isEmpty() || lastNameM.isEmpty() || tel.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || tokenStr.isEmpty()){
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }
        // Convierte los string a los valores necesarios para poder usar el servicio para hacer registo a la bd
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            // Si ocurre un error al convertir las cadenas a enteros, devolver un error 400
            return new ResponseEntity<>("Los campos de edad y token deben ser números enteros válidos", HttpStatus.BAD_REQUEST);
        }

        // Register New User:
        Integer result = userService.RegisterNewUserMethod(username, password,
                email, name, lastNameP, lastNameM, tel, age, gender, tokenStr);
        
        if(result != 1){
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
        // Ejemplo de respuesta exitosa con un mensaje de éxito

        

        // Comprobación de si el registro fue exitoso
       /* if (result != null && result > 0) {
            // Ejemplo de respuesta exitosa con un mensaje de éxito
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        } else {
            // Si ocurrió un error en el registro, puedes devolver un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario");
        }*/
    }
}
