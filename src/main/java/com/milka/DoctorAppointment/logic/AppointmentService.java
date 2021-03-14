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


    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;

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
                checkIfDoctorIsAvailable(doctor, toCreate.getDate())).collect(Collectors.toList());

        return filter.stream().findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public boolean checkIfPatientHasAppointmentsLimit(int id) {
        List<Appointment> list = appointmentRepository.findByPatientPatientId(id);
        return list.size() > 2;
    }

    boolean checkIfDoctorIsAvailable(Doctor doctor, LocalDate date) {

        return !(numberOfAppointments(doctor, date) > 2);
    }

    int numberOfAppointments(Doctor doctor, LocalDate date) {
        return appointmentRepository.getAllByDoctorIdAndDate(doctor.getDoctorId(), date)
                .stream().mapToInt(List::size).sum();
    }

}
