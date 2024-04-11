package com.apiLetItOut.Api.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.AppointmentCalendar;

import jakarta.transaction.Transactional;


@Repository
public interface AppointmentCalendarRepository extends CrudRepository<AppointmentCalendar, Integer> {
    
    // en esta no me capta startHour, dice que no existe sepa pq
    @Query(value= "Select u.startHour, u.endHour FROM appointmentcalendar u WHERE u.userTherapistId=:userTherapistId AND u.date=:date AND u.therapistAcceptance=1 AND u.TAGacceptance!=2", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfTherapistAppointments(@Param("userTherapistId") int userTherapistId, @Param("date") Date date);

    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND therapistAcceptance=1 AND TAGacceptance=1", nativeQuery = true)
    java.util.List<Object[]> findAllAppointmentsFromCalendarTAG(@Param("userTAGId") int userTAGId);
        
    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND u.date=:date AND therapistAcceptance=1 AND TAGacceptance=1", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsFromCalendarTAGByDate(@Param("userTAGId") int userTAGId, @Param("date") Date date);
                        
    @Query(value= "Select COUNT(*) FROM appointmentcalendar", nativeQuery = true)
    int SearchCountAppointmentsTherapistCalendar();
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO appointmentcalendar " + 
            "(userTAGId, userTherapistId, date, startHour, endHour, therapistAcceptance, TAGacceptance) " +
            "VALUES (:userTAGId, :userTherapistId, :date, :startHour, :endHour, :therapistAcceptance, :TAGacceptance)", nativeQuery = true)
    Integer addNewAppointmentFromTherapistCalendar(@Param("userTAGId") int userTAGId,
                        @Param("userTherapistId") int userTherapistId,
                        @Param("date") Date date,
                        @Param("startHour") Date startHour,
                        @Param("endHour") Date endHour,
                        @Param("therapistAcceptance") int therapistAcceptance,
                        @Param("TAGacceptance") int TAGacceptance
                        );

                        
    // en esta no me capta startHour, dice que no existe sepa pq
    @Query(value= "Select u.startHour, u.endHour FROM appointmentcalendar u WHERE u.userTAGId=:userTAGId AND u.date=:date AND u.therapistAcceptance!=2 AND u.TAGacceptance=1", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfUserTagAppointments(@Param("userTAGId") int userTAGId, @Param("date") Date date);

              
}