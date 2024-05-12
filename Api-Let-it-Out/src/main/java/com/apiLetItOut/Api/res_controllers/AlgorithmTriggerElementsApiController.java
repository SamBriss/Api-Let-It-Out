package com.apiLetItOut.Api.res_controllers;

import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.AlgorithmRelaxationTechniquesService;
import com.apiLetItOut.Api.services.AlgorithmTriggerElementsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.time.Duration;
import java.time.Instant;



@RestController
@RequestMapping("api")
public class AlgorithmTriggerElementsApiController {
    
    @Autowired
    AlgorithmRelaxationTechniquesService algorithmRelaxationTechniquesService;

    @Autowired
    AlgorithmTriggerElementsService algorithmTriggerElementsService;

    @PostMapping("algorithmPatterns/algorithm")
    public ResponseEntity algorithmTriggerPatternsAndElements() {
        
        List<Integer> usersTAGIdList = algorithmTriggerElementsService.getAllUserTAGIdMethod();
        if(!usersTAGIdList.isEmpty())
        {
            for(Integer i : usersTAGIdList)
            {

                System.out.println("usertagid int :   "+i);
                int countAttacks = algorithmTriggerElementsService.selectCountAttacksByUserTAGMethod(i);
                int countAttacksManual = algorithmTriggerElementsService.selectCountAttacksManualByUserTAGMethod(i);
                countAttacks = countAttacks + countAttacksManual;

                System.out.println("count attacks:  "+countAttacks);
                int countManualAttacks = algorithmTriggerElementsService.selectCountManualAttacksByUserTAGMethod(i);
                System.out.println("count manualA:  "+countManualAttacks);
                countAttacks = countAttacks + countManualAttacks;
                System.out.println("count attacks total:  "+countAttacks);

                                
                // get lista de elementos por categoria para el usuario TAG
                List<Integer> listElementsLocation = algorithmTriggerElementsService.getAllDictionaryWordsByCategoryAndUserTAGMethod(i, 1);
                if(!listElementsLocation.isEmpty() && countAttacks>0)
                {
                    if(listElementsLocation.size()>1)
                    {
                        List<Double[]> listInfoElementsLocation = calculateRPearson(listElementsLocation, i);
                        List<Double[]> listInfoElementsLocationByRPearson = validateRPearsonMinimum(listInfoElementsLocation, countAttacks);
                        savePatternsAtBD(listInfoElementsLocationByRPearson, i, countAttacks);
                    }
                }
                List<Integer> listElementsDirections = algorithmTriggerElementsService.getAllDictionaryWordsByCategoryAndUserTAGMethod(i, 4);
                if(!listElementsDirections.isEmpty() && countAttacks>0)
                {
                    if(listElementsDirections.size()>1)
                    {
                        List<Double[]> listInfoElementsDirection = calculateRPearson(listElementsDirections, i);
                        List<Double[]> listInfoElementsDirectionByRPearson = validateRPearsonMinimum(listInfoElementsDirection, countAttacks);
                        savePatternsAtBD(listInfoElementsDirectionByRPearson, i, countAttacks);
                    }
                }
                List<Integer> listElementsMomentsOfDay = algorithmTriggerElementsService.getAllDictionaryWordsByCategoryAndUserTAGMethod(i, 9);
                if(!listElementsMomentsOfDay.isEmpty() && countAttacks>0)
                {
                    if(listElementsMomentsOfDay.size()>1)
                    {
                        List<Double[]> listInfoElementsMomentsOfDay = calculateRPearson(listElementsMomentsOfDay, i);
                        List<Double[]> listInfoElementsMomentsOfDayByRPearson = validateRPearsonMinimum(listInfoElementsMomentsOfDay, countAttacks);
                        savePatternsAtBD(listInfoElementsMomentsOfDayByRPearson, i, countAttacks);
                    }
                }
                List<Integer> listElementsTime = algorithmTriggerElementsService.getAllDictionaryWordsByCategoryAndUserTAGMethod(i, 10);
                if(!listElementsTime.isEmpty() && countAttacks>0)
                {
                    if(listElementsTime.size()>1)
                    {
                        List<Double[]> listInfoElementsTime = calculateRPearson(listElementsTime, i);
                        List<Double[]> listInfoElementsTimeByRPearson = validateRPearsonMinimum(listInfoElementsTime, countAttacks);
                        savePatternsAtBD(listInfoElementsTimeByRPearson, i, countAttacks);
                    }
                }
                List<Integer> listElementsActivities = algorithmTriggerElementsService.getAllDictionaryWordsByCategoryAndUserTAGMethod(i, 8);
                if(!listElementsActivities.isEmpty() && countAttacks>0)
                {
                    if(listElementsActivities.size()>1)
                    {
                        List<Double[]> listInfoElementsActivities = calculateRPearson(listElementsActivities, i);
                        List<Double[]> listInfoElementsActivitiesByRPearson = validateRPearsonMinimum(listInfoElementsActivities, countAttacks);
                        savePatternsAtBD(listInfoElementsActivitiesByRPearson, i, countAttacks);
                    }
                }  
            }

        }   
                return ResponseEntity.ok().body("error");
    }

    private List<Double[]> calculateRPearson(List<Integer> listElementsWords, int i)
    {
        List<Double[]> listInfoByElement = new ArrayList<>();
        
        double sumX = 0, sumY = 0;
        for(int loc : listElementsWords)
        {
            System.out.println("wordId loc int:  "+loc);
            Double[] obj = new Double[12];
            obj[0] = Double.valueOf(loc);
            Integer x = algorithmTriggerElementsService.getRepetitionsAnxietyByUserTAGAndWordIdMethod(i, loc); // x
            Integer y = algorithmTriggerElementsService.getRepetitionsTotalByUserTAGAndWordIdMethod(i, loc); // y
           if(x == null){x = 0;}
           if(y == null){y = 0;}
           
            System.out.println("x :  "+x);
            System.out.println("y :  "+y);
            obj[1] = Double.valueOf(x);
            obj[2] = Double.valueOf(y);
            sumX += x;
            sumY += y;
            listInfoByElement.add(obj);
        }
        System.out.println("sumX:  "+sumX);
        System.out.println("sumY:  "+sumY);
        // media (x)
        Double mediaX = sumX / listElementsWords.size();
        // media (y)
        Double mediaY = sumY / listElementsWords.size();

        System.out.println("media X:   "+mediaX);
        System.out.println("media Y:   "+mediaY);

        // xi -mediaX    yi - mediaY
        Double sumaSx = 0.0, sumaSy = 0.0, sumaMu = 0.0;
        for(Double[] loc : listInfoByElement)
        {
            loc[3] = mediaX;
            loc[4] = mediaY;
            // xi - mediaX e yi -mediaY
            loc[5] = loc[1] - mediaX; // (xi - mediaX)
            loc[6] = loc[2] - mediaY; // (yi - mediaY)
            loc[7] = loc[5] * loc[5]; // (xi - mediaX) cuadrado
            loc[8] = loc[6] * loc[6]; // (yi - mediaY) cuadrado
            loc[9] = loc[5] * loc[6]; // (xi - mediaX)(yi - mediaY)
            sumaMu += loc[9];
            sumaSx += loc[7];
            sumaSy += loc[8];
            System.out.println("----------------------------------------------------------");
            System.out.println("para el elemento:  "+loc[0]);
            System.out.println("(xi - mediaX):              "+loc[5]);
            System.out.println("(yi - mediaY):              "+loc[6]);
            System.out.println("(xi - mediaX) cuadrado:     "+loc[7]);
            System.out.println("(yi - mediaY) cuadrado:     "+loc[8]);
            System.out.println("(xi - mediaX)(yi - mediaY): "+loc[9]);
        }

        System.out.println("suma para sx : "+sumaSx);
        System.out.println("suma para sy : "+sumaSy);
        System.out.println("suma para sxy: "+sumaMu);
        Double Sx = Math.sqrt(sumaSx/(listElementsWords.size()-1));
        Double Sy = Math.sqrt(sumaSy/(listElementsWords.size()-1));
        Double Sxy = sumaMu/(listElementsWords.size()-1);

        Double rPearson = Sxy/ (Sx*Sy);
        for(Double[] loc : listInfoByElement)
        {
            loc[10] = rPearson;
        }
        
        System.out.println("valor Sx:      "+Sx);
        System.out.println("valor Sy:      "+Sy);     
        System.out.println("valor Sxy:     "+Sxy);          
        System.out.println("rPearsonLocation:   "+rPearson);
        System.out.println("------------------------------------------------------------------");
   

        return listInfoByElement;

    }

    private List<Double[]> validateRPearsonMinimum(List<Double[]> listInfoElementsWords, int cantAttacks)
    {
        List<Double[]> listInfoElementsWordsByRPearson = new ArrayList<>();
        for(Double[] e : listInfoElementsWords)
        {
            if(e[10]>=.3)
            {
                Double p = e[1] / cantAttacks;
                if(p>1.0)
                {
                    p = 1.0;
                }
                e[11] = p;
                System.out.println("elemento "+e[0]+"    formula:   "+e[1]+"/"+cantAttacks+"  probabilidad:   "+e[11]);
                listInfoElementsWordsByRPearson.add(e);
            }
        }

        return listInfoElementsWordsByRPearson;
    }

    private int savePatternsAtBD(List<Double[]> listInfoElementsLocationByRPearson, int i, int cantAttacks)
    {
        LocalDate fechaActual = LocalDate.now(); 
        String img = createGraphicImg(listInfoElementsLocationByRPearson, i);

        Integer patternIdSearched = algorithmTriggerElementsService.SelectLastTriggerPatternIdByDate(i, Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        int result;
        if(patternIdSearched == null){
            result = algorithmTriggerElementsService.addTriggerPatternMethod(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()), cantAttacks, i, img);
        }
        else{
            result = patternIdSearched;
        }
        System.out.println("result =   "+result);
        if(result > 0 )
        {
            int patternId = algorithmTriggerElementsService.selectCountTriggerPatternsMethod();

            for(Double[] d : listInfoElementsLocationByRPearson)
            {
                int dictionaryWordId = d[0].intValue();
                int count = d[1].intValue();
                Double rPearson = d[10];
                Double probab = d[11]*100;
                int probability = probab.intValue();
                int result2 = algorithmTriggerElementsService.AddTriggerElementMethod(patternId, dictionaryWordId, count, rPearson, probability);
                System.out.println("result2:  "+result2);
            }
        }
        return result;
    }

    public int determineMakingAlgorithm()
    {
        int count = algorithmTriggerElementsService.selectCountPatternsMethod();
        if(count > 0)
        {
            java.sql.Date date = algorithmTriggerElementsService.selectLastDateAlgorithmPatternsMethod();
            
            java.util.Date utilDate = new java.util.Date(date.getTime());

            Instant now = Instant.now();
            Instant dateInstant = utilDate.toInstant();

            long daysDifference = Duration.between(dateInstant, now).toDays();

            if (daysDifference > 13) {
                System.out.println("Han pasado al menos 14 días desde la fecha almacenada.");
                return 1;
            }
            System.out.println("han pasado "+daysDifference+" desde la ultima vez del algoritmo!");
        }
        else
        {
            return 2;
        }   
        return 0;
    }

    private String createGraphicImg(List<Double[]> listWithElementsInfo, int userTAGId)
    {
        // caro aqui pon lo de crear la grafica para un usuario, nomás manda el userId, y los elementos de este regresa el string de la url
        return "null";
    }
}
