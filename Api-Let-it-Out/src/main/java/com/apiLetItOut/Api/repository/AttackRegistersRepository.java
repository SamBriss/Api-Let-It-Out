package com.apiLetItOut.Api.repository;

import java.time.LocalDate;
import java.time.LocalTime;

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
                
                     
}
