package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        Doctor doctor = new Doctor(appointmentDTO.getSpecialization(), true);
        //and
        Patient patient = new Patient();
        var mockPatientRepository = mock(PatientRepository.class);
        when(mockPatientRepository.findById(appointmentDTO.getPatientId())).thenReturn(Optional.of(patient));
        //and
        var mockDoctorRepository = mock(DoctorRepository.class);
        when(mockDoctorRepository.findFirstBySpecialization(appointmentDTO.getSpecialization()))
                .thenReturn(doctor);
        //and
        var mockAppointmentRepository = mock(AppointmentRepository.class, RETURNS_DEEP_STUBS);
        when(mockAppointmentRepository.getAllByDoctorIdAndDate(doctor.getDoctorId(), appointmentDTO.getDate()))
                .thenReturn(Optional.ofNullable(List.of(new Appointment(), new Appointment(), new Appointment())));
        DoctorService doctorService = new DoctorService(mockAppointmentRepository,
                mockDoctorRepository);

        //system under test
        var toTest = new AppointmentService(mockAppointmentRepository,
                mockPatientRepository, mockDoctorRepository, doctorService);

        //when
        var exception = catchThrowable(() -> toTest.createAppointment(appointmentDTO));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}