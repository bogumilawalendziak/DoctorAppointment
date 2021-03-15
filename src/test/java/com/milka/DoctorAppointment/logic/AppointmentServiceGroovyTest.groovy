package com.milka.DoctorAppointment.logic

import com.milka.DoctorAppointment.model.Appointment
import com.milka.DoctorAppointment.model.AppointmentRepository
import com.milka.DoctorAppointment.model.Doctor
import com.milka.DoctorAppointment.model.DoctorRepository
import com.milka.DoctorAppointment.model.PatientRepository
import spock.lang.Specification

import java.time.LocalDate

class AppointmentServiceGroovyTest extends Specification {

    def appointmentRepository = Mock(AppointmentRepository)
    def doctorRepository = Mock(DoctorRepository)
    def patientRepository = Mock(PatientRepository)
    def "numberOfAppointments returns number of appointments"() {

        given:

        List<Appointment> list = [new Appointment(), new Appointment(), new Appointment()]
        appointmentRepository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)
        AppointmentService service = new AppointmentService(appointmentRepository,patientRepository,doctorRepository)
        when:

        def result = service.numberOfAppointments(new Doctor(), LocalDate.now())
        then:
        result == 3

    }

    def "checkIfDoctorIsAvailable returns true if doctor has less than 3 appointments"() {

        given:
        List<Appointment> list = [new Appointment()]
        appointmentRepository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)
        AppointmentService service = new AppointmentService(appointmentRepository,patientRepository,doctorRepository)
        when:
        def result = service.checkIfDoctorIsAvailable(new Doctor(), LocalDate.now())
        then:
        result == true

    }

    def "checkIfDoctorIsAvailable returns false if doctor has more than 2 appointments"() {

        given:
        def appointmentRepository = Mock(AppointmentRepository)
        List<Appointment> list = [new Appointment(), new Appointment(), new Appointment()]
        appointmentRepository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)
        AppointmentService service = new AppointmentService(appointmentRepository,patientRepository,doctorRepository)
        when:
        def result = service.checkIfDoctorIsAvailable(new Doctor(), LocalDate.now())
        then:
        result == false

    }
}
