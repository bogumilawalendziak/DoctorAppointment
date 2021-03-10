package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public Appointment createAppointment(AppointmentDTO toCreate) {
        Patient patient = patientRepository.findById(toCreate.getPatientId()).orElseThrow(IllegalArgumentException::new);

        if (!checkIfPatientHasAppointmentsLimit(patient.getPatientId())) {

            var doctor = getAvailableDoctor(toCreate);

            Appointment appointment = new Appointment(toCreate.getDescription(), patient,
                    doctor.getDoctorId(), toCreate.getDate());
            return appointmentRepository.save(appointment);
        } else throw new IllegalArgumentException();

    }

    public Doctor getAvailableDoctor(AppointmentDTO toCreate) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(toCreate.getSpecialization());
        var filter = doctors.stream().filter(doctor ->
                doctorService.checkIfDoctorIsAvailable(doctor, toCreate.getDate())).collect(Collectors.toList());

        return filter.stream().findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public boolean checkIfPatientHasAppointmentsLimit(int id) {
        List<Appointment> list = appointmentRepository.findByPatientPatientId(id);
        return list.size() > 2;
    }


}
