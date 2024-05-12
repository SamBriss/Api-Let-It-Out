package com.apiLetItOut.Api.res_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Random;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.UserService;

@RestController
@RequestMapping("api")
public class UsersApiController {
    @Autowired
    UserService userService;

    @PostMapping("/user/findUsernameEmail")
    public ResponseEntity<String> postMethodName(@RequestParam("username") String username,
            @RequestParam("email") String email) {

        int result = userService.SearchUsersEmailMethod(username, email);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.FOUND).body("0");
        } else {
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("1");
            }
        }
    }

    @PostMapping("/user/createToken")
    public ResponseEntity<String> createToken(@RequestParam("username") String username,
            @RequestParam("email") String email) {

        java.util.List<Object[]> resultList = userService.findInfoForTokenMethod(username, email);

        Object[] result = resultList.get(0);

        int age = (int) result[0];
        String name = (String) result[1];
        String lastnameP = (String) result[2];
        StringBuilder token = new StringBuilder();
        if (age / 10 > 0) {
            token.append(age);
        } else {
            token.append("0");
            token.append(age);
        }
        int sizeName = name.length();
        if (sizeName <= 8) {
            for (int i = 0; i < sizeName; i++) {
                token.append(name.charAt(i));
            }
        } else {
            for (int i = 0; i < 8; i++) {
                token.append(name.charAt(i));
            }
        }

        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);

        if (sizeName >= 8) {
            token.append(randomNumber);
            int check = userService.updateTokenMethod(token.toString(), username, email);
            if (check == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("0");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("1");
            }
        } else {

            int sizeLastNameP = lastnameP.length();
            if (sizeLastNameP <= (8 - sizeName)) {
                for (int i = 0; i < sizeLastNameP; i++) {
                    token.append(lastnameP.charAt(i));
                }
            } else {
                for (int i = 0; i < (8 - sizeName); i++) {
                    token.append(lastnameP.charAt(i));
                }
            }
        }

        token.append(randomNumber);
        int check = userService.updateTokenMethod(token.toString(), username, email);
        if (check == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("0");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("1");
        }
    }

    @PostMapping("/userProfile/logOut")
    public ResponseEntity<String> logOutProfile(@RequestParam("username") String username,
            @RequestParam("email") String email) {
        int countRows = userService.updateTokenMethod("0", username, email);
        if (countRows > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se pudo cambiar el token");
        }
    }

    @PostMapping("/user/findTel")
    public ResponseEntity<String> postMethodtel(@RequestParam("tel") String tel) {

        int result = userService.SearchUsersByTelMethod(tel);

        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("1");
        } else if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("0");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("error");
        }
    }

    @PostMapping("user/nameTAGByUsername")
    public ResponseEntity<String> getNameUserTAG(@RequestParam("username") String username) {
        int userId = userService.SearchUserTAGMethod(username);
        if (userId > 0) {
            String name = userService.SearchNameMethod(userId);
            name = name + " " + userService.SearchUserLPMethod(userId);
            if (name != null) {
                return ResponseEntity.ok().body(name);
            }
        }
        return ResponseEntity.ok().body("n");
    }

    @PostMapping("/user/updatePassword")
    public ResponseEntity<String> UpdatePassword(@RequestParam("tel") String tel,
            @RequestParam("password") String password) {

        int result = userService.UpdatePasswordMethod(tel, password);

        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("update");
        } else if (result == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("0");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("error");
        }
    }

    @PostMapping("/userTAG/existenciaTAG")
    public ResponseEntity<String> postMethodExistencia(@RequestParam("username") String username) {
        Integer existencia = userService.SearchUserTAGMethod(username);
        if (existencia == null) {
            return ResponseEntity.status(HttpStatus.OK).body("no existe");
        } else if (existencia > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("existe");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("error");
        }
    }
    @PostMapping("/userTAG/verificaVinculacion")
    public ResponseEntity<String> postMethodverificaVinculacion(@RequestParam("username") String username) {
        
        Integer existencia = userService.SearchUserTAGMethod(username);
        if (existencia == null) {
            return ResponseEntity.status(HttpStatus.OK).body("no existe");
        } else if (existencia > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("existe");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("error");
        }
    }
}
