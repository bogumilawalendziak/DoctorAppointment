package com.milka.DoctorAppointment.logic

import com.milka.DoctorAppointment.model.Appointment
import com.milka.DoctorAppointment.model.AppointmentRepository
import com.milka.DoctorAppointment.model.Doctor
import spock.lang.Specification

import java.time.LocalDate

class DoctorServiceTest extends Specification {

    def "numberOfAppointments returns number of appointments"() {

        given:
        def repository = Mock(AppointmentRepository)
        def doctorService = new DoctorService(repository)
        List<Appointment> list = [new Appointment(), new Appointment(), new Appointment()]
        repository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)
        when:
        def result = doctorService.numberOfAppointments(new Doctor(), LocalDate.now())
        then:
        result == 3

    }

    def "checkIfDoctorIsAvailable returns true if doctor has less than 3 appointments"() {

        given:
        def repository = Mock(AppointmentRepository)
        def doctorService = new DoctorService(repository)
        List<Appointment> list = [new Appointment()]
        repository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)

        when:
        def result = doctorService.checkIfDoctorIsAvailable(new Doctor(), LocalDate.now())
        then:
        result == true

    }

    def "checkIfDoctorIsAvailable returns false if doctor has more than 2 appointments"() {

        given:
        def repository = Mock(AppointmentRepository)
        def doctorService = new DoctorService(repository)
        List<Appointment> list = [new Appointment(), new Appointment(), new Appointment()]
        repository.getAllByDoctorIdAndDate(0, LocalDate.now()) >> Optional.of(list)
        when:
        def result = doctorService.checkIfDoctorIsAvailable(new Doctor(), LocalDate.now())
        then:
        result == false

    }
}
