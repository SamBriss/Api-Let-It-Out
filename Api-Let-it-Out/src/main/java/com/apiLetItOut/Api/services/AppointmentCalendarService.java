package com.apiLetItOut.Api.services;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Integer SearchCountAppointmentsTherapistCalendarMethod()
    {
        return appointmentCalendarRepository.SearchCountAppointmentsTherapistCalendar();
    }
    
    public Integer addNewAppointmentFromTherapistCalendarMethod(int userTAGId, int userTherapistId, Date date, Date startHour, Date endHour, int therapistAcceptance, int TAGacceptance)
    {
        return appointmentCalendarRepository.addNewAppointmentFromTherapistCalendar(userTAGId, userTherapistId, date, startHour, endHour, therapistAcceptance, TAGacceptance);
    }
    
}