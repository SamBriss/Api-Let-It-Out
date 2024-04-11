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

import com.apiLetItOut.Api.services.RelationUsersService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import com.apiLetItOut.Api.services.UserTherapistService;

@RestController
@RequestMapping("api")
public class RelationUsersController {
    @Autowired
    RelationUsersService relationUsersService;

    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserTherapistService userTherapistService;

    @PostMapping("/users/AcceptRequest")
    public ResponseEntity postMethodName(@RequestParam("usernameTherapist") String usernameTherapist,
                                        @RequestParam("usernameTAG") String usernameTAG) {

        int userIdTTAG = userService.SearchUserTAGMethod(usernameTAG);
        int userTAGId = userTAGService.FindUserTAGMethod(userIdTTAG);

        int userIdTherapist = userService.SearchUserTAGMethod(usernameTherapist);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdTherapist);

        if (userTAGId > 0) 
        {
            int RegisterVinculation = relationUsersService.RegisterNewRelationUsersMethod(userTAGId, userTherapistId);
            
            if (RegisterVinculation > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("sucess");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar vinculacion");
            }
        } 
        else 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario TAG");
        }
    }

    @PostMapping("/userTherapist/usernameTherapist")
    public ResponseEntity postMethodNameUsernameTherapist(@RequestParam("vinculationCode") String vinculationCodestr) {

        int vinculationCode;
        try {
            vinculationCode = Integer.parseInt(vinculationCodestr);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Los campos de numeros deben ser números enteros válidos",
                    HttpStatus.BAD_REQUEST);
        }

        int userTherapistId = userTherapistService.FindTherapistIdByCodeMethod(vinculationCode);
        int userId = userTherapistService.SearchIdByTherapistMethod(userTherapistId);

        if(userId > 0)
        {
            String username = userService.SearchUsernameByIdMethod(userId);

            if(username != null)
            {
                return ResponseEntity.status(HttpStatus.OK).body(username);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el username");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    @PostMapping("/userTAG/relationTAG")
    public ResponseEntity postMethodNameRelationTAG(@RequestParam("username") String username) {
        try {
            int userId = userService.SearchUserTAGMethod(username);
            int userTAGId = userTAGService.FindUserTAGMethod(userId);
    
            if (userTAGId > 0) 
            {
                List<Integer> therapistIds = relationUsersService.SearchTherapistByTAGMethod(userTAGId);

                if (!therapistIds.isEmpty()) {
                    List<String> therapistUsernames = new ArrayList<>();
                    for (Integer therapistId : therapistIds) {
                        int userIdT = userTherapistService.SearchIdByTherapistMethod(therapistId);
                        String usernameT = userService.SearchUsernameByIdMethod(userIdT);
                        if (usernameT != null) {
                            therapistUsernames.add(usernameT);
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(therapistUsernames);
                } else {
                    // Handle case when therapist relationships are not found
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay relacion");
                }
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
            }
        } catch (NullPointerException e) {
            // Handle NullPointerException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay relacion");
        }
    }

    @PostMapping("/userTherapist/relationTherapist")
    public ResponseEntity postMethodNameRelationTherapist(@RequestParam("username") String username) {
        try {
            int userId = userService.SearchUserTAGMethod(username);
            int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);
    
            if (userTherapistId > 0) 
            {
                List<Integer> TagIds = relationUsersService.SearchTAGByTherapistMethos(userTherapistId);

                if (!TagIds.isEmpty()) {
                    List<String> tagUsernames = new ArrayList<>();
                    for (Integer tagId : TagIds) {
                        int userIdT = userTAGService.FoundIdByTAGMethod(tagId);
                        String usernameT = userService.SearchUsernameByIdMethod(userIdT);
                        if (usernameT != null) {
                            tagUsernames.add(usernameT);
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(tagUsernames);
                } else {
                    // Handle case when therapist relationships are not found
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay relacion");
                }
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
            }
        } catch (NullPointerException e) {
            // Handle NullPointerException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No hay relacion");
        }
    }

    @PostMapping("/userTherapist/RequestQuantityVinculation")
    public ResponseEntity postMethodNameCountVinculation(@RequestParam("username") String username){

        int userId = userService.SearchUserTAGMethod(username);
        int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);

        if(userTherapistId > 0)
        {
            int countRequest = relationUsersService.CountRequestQuantityVinculationMethod(userTherapistId);

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

    @PostMapping("/userTherapist/detachTAG")
    public ResponseEntity postMethodNamedetach(@RequestParam("usernameTherapist") String usernameTherapist,
                                                @RequestParam("usernameTAG") String usernameTAG) {

    int userIdTTAG = userService.SearchUserTAGMethod(usernameTAG);
    int userTAGId = userTAGService.FindUserTAGMethod(userIdTTAG);

    int userIdTherapist = userService.SearchUserTAGMethod(usernameTherapist);
    int userTherapistId = userTherapistService.FoundTherapistIdMethod(userIdTherapist);


        if(userTherapistId > 0 && userTAGId > 0)
        {
            int deleteVinculation = relationUsersService.DeleteVinculationMethod(userTAGId, userTherapistId);

            if (deleteVinculation > 0)
            {
                return ResponseEntity.status(HttpStatus.OK).body("delete");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No eliminado");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
        }
    }

    
    @PostMapping("/addAppointment/relationsTherapist")
    public ResponseEntity postMethodNameRelationTherapistForCalendar(@RequestParam("username") String username) {
        try {
            int userId = userService.SearchUserTAGMethod(username);
            int userTherapistId = userTherapistService.FoundTherapistIdMethod(userId);
    
            if (userTherapistId > 0) 
            {
                List<Integer> TagIds = relationUsersService.SearchTAGByTherapistMethos(userTherapistId);

                if (!TagIds.isEmpty()) {
                    List<String> tagUsernames = new ArrayList<>();
                    for (Integer tagId : TagIds) {
                        int userIdT = userTAGService.FoundIdByTAGMethod(tagId);
                        String name = userService.SearchNameMethod(userIdT);
                        String lastNameP = userService.SearchUserLPMethod(userIdT);
                        String usernameT = userService.SearchUsernameByIdMethod(userIdT);

                        if (usernameT != null) {
                            String send = name + lastNameP +" "+ "("+usernameT+")";
                            tagUsernames.add(send);
                        }
                    }
                    return ResponseEntity.ok().body(tagUsernames);
                } else {
                    // Handle case when therapist relationships are not found
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NoRelation");
                }
            } 
            else 
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar usuario terapeuta");
            }
        } catch (NullPointerException e) {
            // Handle NullPointerException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NoRelation");
        }
    }
}
