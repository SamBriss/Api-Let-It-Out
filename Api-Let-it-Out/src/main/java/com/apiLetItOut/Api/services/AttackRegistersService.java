package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.AttackRegistersRepository;

@Service
public class AttackRegistersService {
    @Autowired
    AttackRegistersRepository attackRegistersRepository;

    public Integer RegisterAttackMethod(LocalDate date, 
                                        LocalTime startHour, 
                                        LocalTime endHour, 
                                        LocalTime duration,
                                        Integer completed,
                                        Integer typeId,
                                        Integer userTAGId)
    {
        return attackRegistersRepository.RegisterAttack(date, startHour, endHour, duration, completed, typeId, userTAGId);
    }

    public Integer SearchAttackIdMethod(LocalDate date, 
                                        LocalTime startHour, 
                                        LocalTime endHour,
                                        Integer userTAGId)
    {
        return attackRegistersRepository.SearchAttackId(date, startHour, endHour, userTAGId);
    }
}
