package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AttackRegistersService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api")
public class PulseraPanicoApiController {
    @Autowired
    UserService userService;

    @Autowired
    UserTAGService userTAGService;

    @Autowired
    AttackRegistersService attackRegistersService;
    
    // agregue de la pulsera
    @PostMapping("users/getAgeByUsername")
    public ResponseEntity postMethodName(@RequestParam("username") String username) {
        //TODO: process POST request
        Integer userId = userService.SearchUserTAGMethod(username);
        if(userId != null)
        {
            int age = userService.SearchUserAgeMethod(userId);
            String gender = userService.SearchUserGenderMethod(userId);
            int pulsera = userTAGService.SearchUserPulsera(userId);
            int userTAgId = userTAGService.FindUserTAGMethod(userId);
            Boolean medsExistence = userTAGService.SearchMedsExistenceTAG(userTAgId);
            String name = userService.SearchNameMethod(userId);
            java.util.List<Object[]> emergencyContacts = userTAGService.SearchEmergencyContactsByUserTAGId(userTAgId);
            
            Object[] obj = new Object[14];
            obj[0] = age;
            obj[1] = gender;
            obj[2] = pulsera;
            obj[3] = medsExistence;
            obj[4] = name;
            int i = 5;
            for(Object[] e : emergencyContacts)
            {
                obj[i] = e[0];
                obj[i+1] = e[1];
                i+=2;
            }
            int size = i;
            Object[] objToSend = new Object[size];
            for(int x = 0; x < size; x++)
            {
                objToSend[x] = obj[x];
            }
            String res = "*"+age+gender+pulsera+medsExistence;
            if(res != null)
            {
                System.out.println("resultado age gender pulsera:  "+res);
                return ResponseEntity.ok().body(objToSend);
            }
        }
        
        return ResponseEntity.ok().body("0");
    }

    @PostMapping("pulsera/saveAnalisisBD")
    public ResponseEntity<String> pulseraSaveAnalisis(@RequestParam("firstBeat") String firstBeatString, 
                                      @RequestParam("maxBeat") String maxBeatString,
                                      @RequestParam("lastBeat") String lastBeatString,
                                      @RequestParam("avgBeats") String avgBeatsString,
                                      @RequestParam("avgAnalisisBeats") String avgAnalisisBeatsString,
                                      @RequestParam("duration") String durationString,
                                      @RequestParam("username") String username ) {

                                        System.out.println("maxbeat: "+maxBeatString+"  lastbeat:");
        Integer userId = userService.SearchUserTAGMethod(username);
        if(userId != null)
        {
            Integer userTAGId = userTAGService.FindUserTAGMethod(userId);
            if(userTAGId != null)
            {
                try {
                    Date date;
                    Date dateTest = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = formatter.format(dateTest);
                    date = formatoFecha.parse(formattedDate);
                    
                    Integer lastRegisterId = attackRegistersService.searchLastAttackRegister(userTAGId, date);
                    if(lastRegisterId != null)
                    {
                        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
                        // ya tengo el attackId tengo todo lo que voy a guardar en la bd, nomás que se guarde
                        double firstBeat = Double.parseDouble(firstBeatString);
                        double maxBeat = Double.parseDouble(maxBeatString);
                        double lastBeat = Double.parseDouble(lastBeatString);
                        double avgBeats = Double.parseDouble(avgBeatsString);
                        double avgAnalisisBeat = Double.parseDouble(avgAnalisisBeatsString);
                        double duration = Double.parseDouble(durationString);
                        System.out.println("lo que se guardara en la bd:");
                        System.out.println("attackRegisterId :  "+lastRegisterId);
                        System.out.println("firstBeat :  "+firstBeat);
                        System.out.println("maxBeat :  "+maxBeat);
                        System.out.println("last beat :  "+lastBeat);
                        System.out.println("");
                        int res = attackRegistersService.UpdateAttackRegisterType(lastRegisterId);
                        System.out.println("resultado de updatear el type del attack: "+res);
                        // mandar el insert
                        int result = attackRegistersService.RegisterAttackPulsera(lastRegisterId, firstBeat, maxBeat, lastBeat, avgBeats, avgAnalisisBeat, avgAnalisisBeat, duration); 
                        if(result == 1)
                        {
                            return ResponseEntity.ok().body("1");
                        }
                        return ResponseEntity.ok().body("0");
                    }
                    return ResponseEntity.ok().body("no last RegisterId today");
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        
        return ResponseEntity.ok().body("error");
    }
    
    @PostMapping("pulsera/registerExistence")
    public ResponseEntity<String> updateExistencePulsera(@RequestParam("username") String username) {
        
        Integer userId = userService.SearchUserTAGMethod(username);
        if(userId != null)
        {
            int res = userTAGService.UpdateExistencePulseraTAG(userId);
            if(res == 1)
            {
                System.out.println("pulsera modificada en usertag");
                return ResponseEntity.ok().body("1");
            }
            System.out.println("pulsera no modificada en userstag");
            return ResponseEntity.ok().body("0");
        }
        return ResponseEntity.ok().body("noUserId");
    }
    
}
