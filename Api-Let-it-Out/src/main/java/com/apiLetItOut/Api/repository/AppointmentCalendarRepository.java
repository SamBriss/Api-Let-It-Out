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
    @Query(value= "Select u.startHour, u.endHour FROM appointmentcalendar u WHERE u.userTherapistId=:userTherapistId AND u.date=:date", nativeQuery = true)
    java.util.List<Object[]> findRegistersOfTherapistAppointments(@Param("userTherapistId") int userTherapistId, @Param("date") Date date);

                    
}