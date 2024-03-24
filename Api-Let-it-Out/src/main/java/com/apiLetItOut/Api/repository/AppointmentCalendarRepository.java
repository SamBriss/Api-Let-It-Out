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

                    
    @Query(value= "Select COUNT(*) FROM activity_therapist_calendar", nativeQuery = true)
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
}