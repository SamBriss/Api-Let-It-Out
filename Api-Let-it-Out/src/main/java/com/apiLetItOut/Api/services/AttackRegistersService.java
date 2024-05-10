package com.apiLetItOut.Api.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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

    public Integer SearchAttackIdForReportsMethod(LocalDate date, 
                                        LocalTime startHour,
                                        Integer userTAGId)
    {
        return attackRegistersRepository.SearchAttackIdForReports(date, startHour, userTAGId);
    }

    public List<Object[]> SearchAttacksOfUserMethod(Integer userTAGId)
    {
        return attackRegistersRepository.SearchAttacksOfUser(userTAGId);
    }

    public List<String> SearchDurations(LocalDate actualDate, LocalDate beforeDate, int userTAGId)
    {
        return attackRegistersRepository.SearchDurations(actualDate, beforeDate, userTAGId);
    }

    public String SearchDurationByAttackIdMethod(int attackRegisterId)
    {
        return attackRegistersRepository.SearchDurationByAttackId(attackRegisterId);
    }

    
    // pulsera
    public Integer searchLastAttackRegister(int userTAGId, Date date)
    {
        return attackRegistersRepository.searchLastAttackRegister(userTAGId, date);
    }
    public Integer RegisterAttackPulsera(int attackRegisterId, double firstBeat, double maxBeat, double lastBeat, double avgBeats, double avgAnalisisBeats, double lastAnalisisBeat, double duration)
    {
        return attackRegistersRepository.RegisterAttackPulsera(attackRegisterId, firstBeat, maxBeat, lastBeat, avgBeats, avgAnalisisBeats, lastAnalisisBeat, duration);
    }
    public Integer UpdateAttackRegisterType(int attackRegisterId)
    {
        return attackRegistersRepository.UpdateAttackRegisterType(attackRegisterId);
    }
}
