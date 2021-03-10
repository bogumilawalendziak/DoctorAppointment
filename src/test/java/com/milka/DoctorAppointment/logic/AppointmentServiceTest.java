package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {


    @Test
    void createAppointment_if_cant_save_appointments_return_IllegalArgumentException() {

        //given
        AppointmentDTO appointmentDTO = new AppointmentDTO(Specialization.NEUROLOGY,
                "description", 1, LocalDate.now());
        //and
        DoctorService doctorService = new DoctorService(appointmentRepositoryReturningNumberOfAppointments(3),
               doctorRepositoryReturningDoctor(1));
        //system under test
        var toTest = new AppointmentService(appointmentRepositoryReturningNumberOfAppointments(3),
               patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var exception = catchThrowable(() -> toTest.createAppointment(appointmentDTO));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createAppointment_if_can_save_appointments_returns_appointment() {

        //given
        AppointmentDTO appointmentDTO = new AppointmentDTO(Specialization.NEUROLOGY,
                "description", 1, LocalDate.now());
        //and
        var repository = appointmentRepositoryReturningNumberOfAppointments(0);
        DoctorService doctorService = new DoctorService(repository,
                doctorRepositoryReturningDoctor(1));
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when+then
        assertThat(toTest.createAppointment(appointmentDTO)).isInstanceOf(Appointment.class);
    }

    @Test
    void getAvailableDoctor_returns_IllegalArgumentException_if_there_are_no_available_doctors() {

        //given
        AppointmentDTO appointmentDTO = new AppointmentDTO(Specialization.NEUROLOGY,
                "description", 1, LocalDate.now());
        //and
        var repository = appointmentRepositoryReturningNumberOfAppointments(3);
        DoctorService doctorService = new DoctorService(repository,
                doctorRepositoryReturningDoctor(0));
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(0), doctorService);
        //when
        var exception = catchThrowable(()->toTest.getAvailableDoctor(appointmentDTO));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getAvailableDoctor_returns_Doctor_if_is_available_doctor() {

        //given
        AppointmentDTO appointmentDTO = new AppointmentDTO(Specialization.NEUROLOGY,
                "description", 1, LocalDate.now());
        //and
        var repository = appointmentRepositoryReturningNumberOfAppointments(0);
        DoctorService doctorService = new DoctorService(repository,
                doctorRepositoryReturningDoctor(1));
        //system under test
        var toTest = new AppointmentService(repository,
                patientRepositoryReturningPatient(), doctorRepositoryReturningDoctor(1), doctorService);
        //when
        var doctor = toTest.getAvailableDoctor(appointmentDTO);
        // then
        assertThat(doctor).isInstanceOf(Doctor.class);
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


    private AppointmentRepository appointmentRepositoryReturningNumberOfAppointments(int appointments) {
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < appointments; i++) {
            list.add(new Appointment());
        }
        var mockAppointmentRepository = mock(AppointmentRepository.class, RETURNS_DEEP_STUBS);
        when(mockAppointmentRepository.getAllByDoctorIdAndDate(anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.ofNullable(list));
        return mockAppointmentRepository;
    }



}