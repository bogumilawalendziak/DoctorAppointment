package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.AppointmentRepository;
import com.milka.DoctorAppointment.model.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorService {

    private final AppointmentRepository appointmentRepository;


    public DoctorService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    boolean checkIfDoctorIsAvailable(Doctor doctor, LocalDate date) {

        return !(numberOfAppointments(doctor, date) > 2);
    }

    int numberOfAppointments(Doctor doctor, LocalDate date) {
        return appointmentRepository.getAllByDoctorIdAndDate(doctor.getDoctorId(), date)
                .stream().mapToInt(List::size).sum();
    }
}
