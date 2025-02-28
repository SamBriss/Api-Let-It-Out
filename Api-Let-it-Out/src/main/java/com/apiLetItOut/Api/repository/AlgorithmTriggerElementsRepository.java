package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.TriggerElements;
import java.util.Date;

import jakarta.transaction.Transactional;

@Repository
public interface AlgorithmTriggerElementsRepository extends CrudRepository<TriggerElements, Integer>  {

    @Query(value = "Select c.dictionaryWordId from dictionarycount c INNER JOIN dictionarywords w ON c.dictionaryWordId=w.dictionaryWordId WHERE w.categoryId=:categoryId AND c.userTAGId=:userTAGId AND c.attackStatus=1 GROUP BY c.dictionaryWordId", nativeQuery = true)
    List<Integer> getAllDictionaryWordsByCategoryAndUserTAG(@Param("userTAGId") int userTAGId,
                                                            @Param("categoryId") int categoryId);

    @Query(value = "Select SUM(c.repetitions) FROM dictionarycount c WHERE c.userTAGId=:userTAGId AND c.dictionaryWordId=:wordId GROUP BY c.dictionaryWordId",
    nativeQuery = true)
    int getRepetitionsTotalByUserTAGAndWordId(@Param("userTAGId") int userTAGId, @Param("wordId") int wordId);

    @Query(value = "Select SUM(c.repetitions) FROM dictionarycount c WHERE c.userTAGId=:userTAGId AND c.dictionaryWordId=:wordId AND c.attackStatus=1 GROUP BY c.dictionaryWordId",
    nativeQuery = true)
    Integer getRepetitionsAnxietyByUserTAGAndWordId(@Param("userTAGId") int userTAGId, @Param("wordId") int wordId);

    @Query(value = "Select userTAGId from dictionarycount GROUP BY userTAGId", nativeQuery = true)
    List<Integer> getAllUserTAGId();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO triggerpatterns (date, totalAttacks, userTAGId, graphicImg) VALUES(:date, :totalAttacks, :userTAGId, :img)", nativeQuery = true)
    Integer addTriggerPattern(@Param("date") Date date,
                        @Param("totalAttacks") int totalAttacks,
                        @Param("userTAGId") int userTAGId,
                        @Param("img") String img);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO triggerelements (triggerPatternId, dictionaryWordId, count, rPearson, individualProbability) VALUES(:triggerPatternId, :dictionaryWordId, :count, :rPearson, :individualProbability)", nativeQuery = true)
    Integer AddTriggerElement(@Param("triggerPatternId") int triggerPatterId,
                        @Param("dictionaryWordId") int dictionaryWordId,
                        @Param("count") int count,
                        @Param("rPearson") double rPearson,
                        @Param("individualProbability") int individualProbability);

    @Query(value = "SELECT triggerPatternId FROM triggerPatterns ORDER BY triggerPatternId DESC LIMIT 1", nativeQuery = true)
    int selectCountTriggerPatterns();

    @Query(value = "Select date from triggerPatterns ORDER BY date DESC LIMIT 1", nativeQuery = true)
    java.sql.Date selectLastDateAlgorithmPatterns();
    
    @Query(value = "Select COUNT(*) from triggerPatterns", nativeQuery = true)
    int selectCountPatterns();
    
    @Query(value = "Select COUNT(attackRegisterId) from attackregisters WHERE userTAGId=:userTAGId", nativeQuery = true)
    int selectCountAttacksByUserTAG(@Param("userTAGId") int userTAGId);

    
    @Query(value = "Select COUNT(manualAttackRegister) from manualattackregister WHERE userTAGId=:userTAGId", nativeQuery = true)
    int selectCountManualAttacksByUserTAG(@Param("userTAGId") int userTAGId);

// aplicacion del algoritmo de patrones desencadenantes
    @Query(value = "Select triggerpatternId from triggerpatterns WHERE userTAGId=:userTAGId ORDER BY(triggerpatternId) DESC LIMIT 1", nativeQuery = true)
    Integer SelectLastTriggerPatternId(@Param("userTAGId") int userTAGId);
    
    @Query(value = "Select individualProbability from triggerelements WHERE dictionaryWordId=:dictionaryWordId AND triggerPatternId=:triggerPatternId", nativeQuery = true)
    Integer selectIndividualProbabilityDesencadenanteWord(@Param("dictionaryWordId") int dictionaryWordId,
                                                        @Param("triggerPatternId") int triggerPatternId);
                                                        
    @Query(value = "Select triggerpatternId from triggerpatterns WHERE userTAGId=:userTAGId AND date=:date", nativeQuery = true)
    Integer SelectLastTriggerPatternIdByDate(@Param("userTAGId") int userTAGId,
                                            @Param("date") Date date);
                                            
    @Query(value = "Select COUNT(manualAttackRegister) from manualattackregister WHERE userTAGId=:userTAGId", nativeQuery = true)
    int selectCountAttacksManualByUserTAG(@Param("userTAGId") int userTAGId);

    @Query(value = "Select triggerPatternId from triggerPatterns where userTAGId=:userTAGId", nativeQuery = true)
    List<Integer> SearchPatternsIdByUserTAGId(@Param("userTAGId") int userTAGId);

    @Query(value = "Select triggerElementId from triggerElements where triggerPatternId=:triggerPatternId", nativeQuery = true)
    List<Integer> SearchElementsByPatternId(@Param("triggerPatternId") int triggerPatternId);

    @Query(value="Select d.dictionaryWordId from triggerPatterns p JOIN "+
    "triggerElements e on e.triggerPatternId= p.triggerPatternId JOIN dictionaryWords d ON e.dictionaryWordId = d.dictionaryWordId "+
    " where p.userTAGId=:userTAGId AND d.categoryId=:categoryId  GROUP BY d.word ", nativeQuery = true)
    List<Integer> SearchWordsPatterns(@Param("userTAGId") int userTAGId,
                                        @Param("categoryId") int categoryId);

    @Query(value = "Select p.date, e.individualProbability, d.word from triggerPatterns p JOIN triggerElements e on e.triggerPatternId= p.triggerPatternId JOIN dictionaryWords d ON e.dictionaryWordId = d.dictionaryWordId  where e.dictionaryWordId=:dictionaryWordId AND p.userTAGId=:userTAGId", nativeQuery = true)
    List<Object[]> SearchPointGraphicByWordId(@Param("userTAGId") int userTAGId,
                                            @Param("dictionaryWordId") int dictionaryWordId);
    
    @Query(value = "Select date from triggerPatterns group by date order by date ASC", nativeQuery = true)
    List<Date> SearchDatesOfPatterns();

    @Query(value = "Select count(*) from triggerPatterns p JOIN triggerElements e on e.triggerPatternId= p.triggerPatternId JOIN dictionaryWords d ON e.dictionaryWordId = d.dictionaryWordId where e.dictionaryWordId=:dictionaryWordId AND p.userTAGId=:userTAGId", nativeQuery = true)
    Integer SearchCountOfWordsRepetition(@Param("userTAGId") int userTAGId,
                                            @Param("dictionaryWordId") int dictionaryWordId);

}
