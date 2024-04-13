package com.apiLetItOut.Api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.models.AppointmentCalendar;
import com.apiLetItOut.Api.repository.AppointmentCalendarRepository;

@Service
public class AppointmentCalendarService {
    @Autowired
    AppointmentCalendarRepository appointmentCalendarRepository;

    public java.util.List<Object[]> findRegistersOfTherapistAppointmentsMethod(int userTherapistId, Date date) {
        return appointmentCalendarRepository.findRegistersOfTherapistAppointments(userTherapistId, date);
    }

    public java.util.List<Object[]> findRegistersOfUserTagAppointmentsMethod(int userTAGId, Date date) {
        return appointmentCalendarRepository.findRegistersOfUserTagAppointments(userTAGId, date);
    }
  
    public java.util.List<Object[]> findAllAppointmentsFromCalendarTAGMethod(int userTAGId)
    {
        return appointmentCalendarRepository.findAllAppointmentsFromCalendarTAG(userTAGId);
    }
    
    public java.util.List<Object[]> findAllAppointmentsFromCalendarTherapistMethod(int userTherapistId)
    {
        return appointmentCalendarRepository.findAllAppointmentsFromCalendarTherapist(userTherapistId);
    }

    public java.util.List<Object[]> findAppointmentsFromCalendarTAGByDateMethod(int userTAGId, Date date)
    {
        return appointmentCalendarRepository.findAppointmentsFromCalendarTAGByDate(userTAGId, date);
    }
    
    public java.util.List<Object[]> findAppointmentsFromCalendarTherapistByDateMethod(int userTherapistId, Date date)
    {
        return appointmentCalendarRepository.findAppointmentsFromCalendarTherapistByDate(userTherapistId, date);
    }

    public Integer SearchCountAppointmentsTherapistCalendarMethod()
    {
        return appointmentCalendarRepository.SearchCountAppointmentsTherapistCalendar();
    }
    
    public Integer addNewAppointmentFromTherapistCalendarMethod(int userTAGId, int userTherapistId, Date date, Date startHour, Date endHour, int therapistAcceptance, int TAGacceptance)
    {
        return appointmentCalendarRepository.addNewAppointmentFromTherapistCalendar(userTAGId, userTherapistId, date, startHour, endHour, therapistAcceptance, TAGacceptance);
    }

    public java.util.List<Object[]> findNext14DaysAppointmentsMethod(String username)
    {
        return appointmentCalendarRepository.findNext14DaysAppointments(username);
    }
    
    public java.util.List<Object[]> findDateAndHoursOfAppointmentsTherapistMethod(String username)
    {
        return appointmentCalendarRepository.findDateAndHoursOfAppointmentsTherapist(username);
    }

    public java.util.List<Object[]> findDateAndHoursOfAppointmentsTAGMethod(String username)
    {
        return appointmentCalendarRepository.findDateAndHoursOfAppointmentsTAG(username);
    }

    public Integer UpdateAppointmentMethod(int appointmentId, Date startHour, Date endHour, Date date, int therapistAcceptance, int TAGacceptance)
    {
        return appointmentCalendarRepository.UpdateAppointment(appointmentId, startHour, endHour, date, therapistAcceptance, TAGacceptance);
    }
    
    public java.util.List<Object[]> findAppointmentsToConfirmByTherapistMethod(String username)
    {
        return appointmentCalendarRepository.findAppointmentsToConfirmByTherapist(username);
    }
    public java.util.List<Object[]> findAllAppointmentsToAcceptOrDeclineFromCalendarTherapistMethod(int userTherapistId)
    {
        return appointmentCalendarRepository.findAllAppointmentsToAcceptOrDeclineFromCalendarTherapist(userTherapistId);
    }
    public int updateTherapistAcceptanceMethod(int appointmentId, int therapistAcceptance)
    {
        return appointmentCalendarRepository.updateTherapistAcceptance(appointmentId, therapistAcceptance);
    }
}