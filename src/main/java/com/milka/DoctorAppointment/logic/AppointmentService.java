package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.doctorService = doctorService;
    }

    public void deleteAppointment(int id) {
        int doctorId = appointmentRepository.findById(id).getDoctorId();
        LocalDate date = appointmentRepository.findById(id).getDate();
        appointmentRepository.deleteAppointmentById(id);
        doctorService.checkIfDoctorIsAvailable(doctorRepository.findById(doctorId)
                .orElseThrow(IllegalArgumentException::new), date);
    }

    /**
     * create appointment from DTO
     * if doctorIsAvailable return 204
     * else return 400
     */
    public void createAppointment(AppointmentDTO toCreate) {
        Specialization specialization = toCreate.getSpecialization();
        Patient patient = patientRepository.findById(toCreate.getPatientId()).orElseThrow(IllegalArgumentException::new);
        Doctor doctor = doctorRepository.findFirstBySpecialization(specialization);


        if (doctorService.checkIfDoctorIsAvailable(doctor, toCreate.getDate())) {
            Appointment appointment = new Appointment(toCreate.getDescription(), patient,
                    doctor.getDoctorId(), toCreate.getDate());
            appointmentRepository.save(appointment);
        } else {

            throw new IllegalArgumentException("Illegal state");
        }
    }
}
