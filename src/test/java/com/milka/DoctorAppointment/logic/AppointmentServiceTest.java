package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    AppointmentDTO appointmentDTO = new AppointmentDTO(Specialization.NEUROLOGY,
            "description", 1, LocalDate.now());

    @Test
    void createAppointment_if_cant_save_appointments_return_IllegalArgumentException() {

        //given
        DoctorService doctorService = new DoctorService(appointmentRepositoryReturningDoctorsAppointments(3)
               );
        //system under test
        var toTest = new AppointmentService(appointmentRepositoryReturningDoctorsAppointments(3),
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var exception = catchThrowable(() -> toTest.createAppointment(appointmentDTO));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createAppointment_if_can_save_appointments_returns_appointment() {

        //given
        var repository = appointmentRepositoryReturningDoctorsAppointments(0);
        DoctorService doctorService = new DoctorService(repository);
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when+then
        assertThat(toTest.createAppointment(appointmentDTO)).isInstanceOf(Appointment.class);
    }

    @Test
    void getAvailableDoctor_returns_IllegalArgumentException_if_there_are_no_available_doctors() {

        //given
        var repository = appointmentRepositoryReturningDoctorsAppointments(3);
        DoctorService doctorService = new DoctorService(repository);
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(0), doctorService);
        //when
        var exception = catchThrowable(() -> toTest.getAvailableDoctor(appointmentDTO));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getAvailableDoctor_returns_Doctor_if_is_available_doctor() {

        //given
        var repository = appointmentRepositoryReturningDoctorsAppointments(0);
        DoctorService doctorService = new DoctorService(repository);
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var doctor = toTest.getAvailableDoctor(appointmentDTO);
        // then
        assertThat(doctor).isInstanceOf(Doctor.class);
    }

    @Test
    void checkIfPatientHasAppointmentsLimit_returns_false_if_patient_has_less_than_3_appointments() {

        //given
        var repository = appointmentRepositoryReturningPatientAppointments(0);
        DoctorService doctorService = new DoctorService(repository);
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var result = toTest.checkIfPatientHasAppointmentsLimit(1);
        // then
        assertThat(result).isFalse();
    }

    @Test
    void checkIfPatientHasAppointmentsLimit_returns_true_if_patient_has_more_than_2_appointments() {

        //given
        var repository = appointmentRepositoryReturningPatientAppointments(3);
        DoctorService doctorService = new DoctorService(repository);
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var result = toTest.checkIfPatientHasAppointmentsLimit(1);
        // then
        assertThat(result).isTrue();
    }


    private PatientRepository patientRepositoryReturningPatient() {
        var mockPatientRepository = mock(PatientRepository.class);
        when(mockPatientRepository.findById(anyInt())).thenReturn(Optional.of(new Patient()));
        return mockPatientRepository;
    }


    private DoctorRepository doctorRepositoryReturningDoctor(int doctors) {
        List<Doctor> list = new ArrayList<>();
        for (int i = 0; i < doctors; i++) {
            list.add(new Doctor());
        }
        var mockDoctorRepository = mock(DoctorRepository.class);
        when(mockDoctorRepository.findBySpecialization(any(Specialization.class)))
                .thenReturn(list);

        return mockDoctorRepository;
    }


    private AppointmentRepository appointmentRepositoryReturningDoctorsAppointments(int appointments) {
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < appointments; i++) {
            list.add(new Appointment());
        }
        var mockAppointmentRepository = mock(AppointmentRepository.class, RETURNS_DEEP_STUBS);
        when(mockAppointmentRepository.getAllByDoctorIdAndDate(anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.ofNullable(list));
        return mockAppointmentRepository;
    }

    private AppointmentRepository appointmentRepositoryReturningPatientAppointments(int appointments) {
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < appointments; i++) {
            list.add(new Appointment());
        }
        var mockAppointmentRepository = mock(AppointmentRepository.class, RETURNS_DEEP_STUBS);
        when(mockAppointmentRepository.findByPatientPatientId(anyInt())).thenReturn(list);
        return mockAppointmentRepository;
    }

}