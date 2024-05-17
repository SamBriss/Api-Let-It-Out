package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

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

    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND therapistAcceptance=1 AND TAGacceptance=1 ORDER BY(u.date) ASC", nativeQuery = true)
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

    
    // nuevo commit de calendario terapueta y chatbot
    
    @Query(value = "Select a.appointmentId, a.date, a.startHour, a.endHour, a.userTherapistId FROM appointmentcalendar a INNER JOIN usersTAG t ON a.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.TAGacceptance=1 AND a.date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 14 DAY) ORDER BY a.date ASC", nativeQuery = true)
    java.util.List<Object[]> findNext14DaysAppointments(@Param("username") String username);

    @Query(value = "Select a.startHour, a.endHour, a.date from appointmentcalendar a INNER JOIN userstherapists t ON a.userTherapistId=t.userTherapistId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.TherapistAcceptance=1 AND a.date>= DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY a.startHour ASC", nativeQuery = true)
    List<Object[]> findDateAndHoursOfAppointmentsTherapist(@Param("username") String username);

    @Query(value = "Select a.startHour, a.endHour, a.date from appointmentcalendar a INNER JOIN usersTAG t ON a.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.TAGacceptance=1 AND a.date>= DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY a.startHour ASC", nativeQuery = true)
    List<Object[]> findDateAndHoursOfAppointmentsTAG(@Param("username") String username);
    
    @Transactional
    @Modifying
    @Query(value = "Update appointmentcalendar set startHour =:startHour, endHour = :endHour, date = :date, therapistAcceptance = :therapistAcceptance, TAGacceptance =:TAGacceptance where appointmentId=:appointmentId", nativeQuery = true)
    Integer UpdateAppointment(@Param("appointmentId") int appointmentId, 
                        @Param("startHour") Date startHour,
                        @Param("endHour") Date endHour,
                        @Param("date") Date date,
                        @Param("therapistAcceptance") int therapistAcceptance,
                        @Param("TAGacceptance") int TAGacceptance);

                        
    @Query(value = "Select a.appointmentId, a.endHour, a.userTherapistId FROM appointmentcalendar a INNER JOIN usersTAG t ON a.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.TherapistAcceptance=1 AND a.TAGacceptance=0 ORDER BY a.date ASC", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsToConfirmByTherapist(@Param("username") String username);


    // therapist calendar appointment
    
    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN usersTAG t ON u.userTAGId=t.userTAGId INNER JOIN users i ON i.userId=t.userId WHERE u.userTherapistId=:userTherapistId AND therapistAcceptance=1 AND TAGacceptance=1", nativeQuery = true)
    java.util.List<Object[]> findAllAppointmentsFromCalendarTherapist(@Param("userTherapistId") int userTherapistId);
     
    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN usersTAG t ON u.userTAGId=t.userTAGId INNER JOIN users i ON i.userId=t.userId WHERE u.userTherapistId=:userTherapistId AND u.date=:date AND therapistAcceptance=1 AND TAGacceptance=1", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsFromCalendarTherapistByDate(@Param("userTherapistId") int userTherapistId, @Param("date") Date date);
   
    // solicitudes appointments para terapeuta
    
    @Query(value= "Select u.appointmentId, u.date, u.startHour, u.endHour, i.name, i.lastnameP, i.username FROM appointmentcalendar u INNER JOIN usersTAG t ON u.userTAGId=t.userTAGId INNER JOIN users i ON i.userId=t.userId WHERE u.userTherapistId=:userTherapistId AND therapistAcceptance=0", nativeQuery = true)
    java.util.List<Object[]> findAllAppointmentsToAcceptOrDeclineFromCalendarTherapist(@Param("userTherapistId") int userTherapistId);

    @Transactional
    @Modifying
    @Query(value = "Update appointmentcalendar set therapistAcceptance =:therapistAcceptance WHERE appointmentId=:appointmentId", nativeQuery = true)
    int updateTherapistAcceptance(@Param("appointmentId") int appointmentId, @Param("therapistAcceptance") int therapistAcceptance);
        
    // reprogramar cita desde el terapeuta
    
    @Query(value= "Select u.appointmentId, i.name, i.lastnameP, i.username, u.date, u.startHour, u.endHour FROM appointmentcalendar u INNER JOIN userstag t ON u.userTAGId=t.userTAGId INNER JOIN users i ON i.userId=t.userId WHERE u.userTherapistId=:userTherapistId AND therapistAcceptance=1 AND TAGacceptance=1 AND u.date>= DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY(u.date) ASC", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsReprogramFromCalendarTAG(@Param("userTherapistId") int userTherapistId);
  
    @Query(value = "Select a.appointmentId, a.startHour, a.endHour, a.date, a.userTherapistId FROM appointmentcalendar a INNER JOIN usersTAG t ON a.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username AND a.TherapistAcceptance=3 AND a.TAGacceptance=0 ORDER BY a.date ASC", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsToReprogramByTherapist(@Param("username") String username);

    
    @Query(value= "Select u.appointmentId, u.startHour, u.endHour, u.date, u.userTherapistId, i.name, i.lastnameP, i.username FROM appointmentcalendar u INNER JOIN userstherapists t ON u.userTherapistId=t.userTherapistId INNER JOIN users i ON i.userId=t.userId WHERE u.userTAGId=:userTAGId AND therapistAcceptance=3 AND TAGacceptance=0 AND u.date>= DATE_ADD(CURDATE(), INTERVAL 1 DAY) ORDER BY(u.date) ASC", nativeQuery = true)
    java.util.List<Object[]> findAppointmentsReprogramForTAG(@Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value = "Update appointmentcalendar set TAGacceptance =:therapistAcceptance WHERE appointmentId=:appointmentId", nativeQuery = true)
    int updateTAGAcceptance(@Param("appointmentId") int appointmentId, @Param("therapistAcceptance") int therapistAcceptance);

    @Transactional
    @Modifying
    @Query(value = "Delete from appointmentcalendar WHERE appointmentId=:appointmentId", nativeQuery = true)
    int deleteAppointment(@Param("appointmentId") int appointmentId);       
        
}