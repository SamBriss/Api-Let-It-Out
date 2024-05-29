package com.apiLetItOut.Api.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.AttackRegisters;

import jakarta.transaction.Transactional;

@Repository
public interface AttackRegistersRepository extends CrudRepository<AttackRegisters, Integer>{
        @Transactional
        @Modifying
        @Query(value = "INSERT INTO attackregisters " +
                "(date, startHour, endHour, duration, completed, typeId, userTAGId) " +
                "VALUES (:date, :startHour, :endHour, :duration, :completed, :typeId, :userTAGId)", nativeQuery = true)
        Integer RegisterAttack(@Param("date") LocalDate date,
                        @Param("startHour") LocalTime startHour,
                        @Param("endHour") LocalTime endHour,
                        @Param("duration") LocalTime duration,
                        @Param("completed") Integer completed,
                        @Param("typeId") Integer typeId,
                        @Param("userTAGId") Integer userTAGId);

        @Query(value = "SELECT attackRegisterId FROM attackregisters WHERE date = :date " +
        "AND startHour = :startHour AND endHour = :endHour AND userTAGId = :userTAGId", nativeQuery = true)
        Integer SearchAttackId(@Param("date") LocalDate date,
                                @Param("startHour") LocalTime startHour,
                                @Param("endHour") LocalTime endHour,
                                @Param("userTAGId") Integer userTAGId);

        @Query(value = "SELECT attackRegisterId FROM attackregisters WHERE date = :date " +
        "AND startHour = :startHour  AND userTAGId = :userTAGId ", nativeQuery = true)
        Integer SearchAttackIdForReports(@Param("date") LocalDate date,
                                @Param("startHour") LocalTime startHour,
                                @Param("userTAGId") Integer userTAGId);

        @Query(value = "Select date, startHour, endHour from attackregisters where userTAGId=:userTAGId AND completed=1 ORDER BY date ASC", nativeQuery = true)
        List<Object[]> SearchAttacksOfUser(@Param("userTAGId") int userTAGId);

        @Query(value="Select duration from attackRegisters where date <= CONCAT('', :actualDate, '') AND date>= CONCAT('', :beforeDate, '') AND userTAGId = :userTAGId", nativeQuery=true)
        List<String> SearchDurations(@Param("actualDate") LocalDate actualDate,
                                        @Param("beforeDate") LocalDate beforeDate,
                                        @Param("userTAGId") int userTAGId); 

        @Query(value = "Select duration from attackRegisters where attackRegisterId=:attackRegisterId", nativeQuery=true)
        String SearchDurationByAttackId (@Param("attackRegisterId") int attackRegisterId);
   
        @Query(value = "Select attackRegisterId, date, startHour from attackregisters where userTAGId=:userTAGId AND completed=0 ORDER BY date ASC", nativeQuery = true)
        List<Object[]> SearchAttacksOfUserIncompleted(@Param("userTAGId") int userTAGId);         

        @Query(value = "SELECT COUNT(*) from attackregisters where userTAGId=:userTAGId AND completed=0", nativeQuery = true)
        int QuantityAttacksOfUserIncompleted(@Param("userTAGId") int userTAGId); 
        
        @Transactional
        @Modifying
        @Query(value = "Update attackregisters set completed = :completed where attackRegisterId =:attackRegisterId", nativeQuery = true)
        Integer UpdateCompletedAttackRegister(@Param("attackRegisterId") int attackRegisterId, 
                                                @Param("completed") int completed);                   
        // pulsera
        @Query(value = "Select attackRegisterId FROM attackregisters WHERE userTAGId=:userTAGId AND date=:date ORDER BY(attackRegisterId) DESC LIMIT 1", nativeQuery = true)
        Integer searchLastAttackRegister(@Param("userTAGId") int userTAGId,
                                                @Param("date") java.util.Date date);

        @Transactional
        @Modifying
        @Query(value = "INSERT INTO attackPulsera(attackRegisterId, firstBeat, maxBeat, lastBeat, avgBeats, avgAnalisisBeats, lastAnalisisBeat, duration) VALUES(:attackRegisterId, :firstBeat, :maxBeat, :lastBeat, :avgBeats, :avgAnalisisBeats, :lastAnalisisBeat, :duration)", nativeQuery = true)
        Integer RegisterAttackPulsera(@Param("attackRegisterId") int attackRegisterId, 
                                        @Param("firstBeat") double firstBeat,
                                        @Param("maxBeat") double maxBeat,
                                        @Param("lastBeat") double lastBeat,
                                        @Param("avgBeats") double avgBeats,
                                        @Param("lastAnalisisBeat") double lastAnalisisBeat,
                                        @Param("avgAnalisisBeats") double avgAnalisisBeats,
                                        @Param("duration") double duration);
                     
        @Transactional
        @Modifying
        @Query(value = "Update attackregisters set typeId =1 WHERE attackRegisterId=:attackRegisterId", nativeQuery = true)
        int UpdateAttackRegisterType(@Param("attackRegisterId") int attackRegisterId);

        @Query(value="Select count(*) from attackRegisters where userTAGId = :userTAGId and date>=CONCAT('', :lastMonth, '')", nativeQuery = true)
        Integer SearchCountOfAttacks(@Param("userTAGId") int userTAGId, @Param("lastMonth") LocalDate lastMonth);

        @Query(value = "Select attackRegisterId from attackregisters WHERE userTAGId = :userTAGId AND completed=1", nativeQuery = true)
        List<Integer> SearchRegistersAttackCompleted(@Param("userTAGId") int userTAGId); 
}
