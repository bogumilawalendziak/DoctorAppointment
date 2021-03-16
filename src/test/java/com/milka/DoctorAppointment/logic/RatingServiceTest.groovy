package com.milka.DoctorAppointment.logic

import com.milka.DoctorAppointment.model.*
import spock.lang.Specification

class RatingServiceTest extends Specification {

    def appointmentRepository = Mock(AppointmentRepository)
    def doctorRepository = Mock(DoctorRepository)
    def ratingRepository = Mock(RatingRepository)
    RatingService service = new RatingService(appointmentRepository, doctorRepository, ratingRepository)

    def "calculateRatingAverage is returning average of ratings"() {
        given:
        Set<Rating> set = new HashSet<>()
        Doctor doctor = new Doctor(new User(), Specialization.DERMATOLOGY, set)
        Rating rate1 = new Rating("description", doctor, 1, number);
        Rating rate2 = new Rating("description", doctor, 2, 2);
        doctor.rating.add(rate1)
        doctor.rating.add(rate2)
        doctor.setDoctorId(1)

        doctorRepository.findById(1) >> Optional.of(doctor)
        when:
        def result = service.calculateRatingAverage(doctor.doctorId)
        then:
        result == expectedResult
        where:
        number || expectedResult
        1      || 1.5
        2      || 2.0
        5      || 3.5

    }

    def "setNewDoctorsRate is setting average of ratings"() {
        given:
        Set<Rating> set = new HashSet<>()
        Doctor doctor = new Doctor(new User(), Specialization.DERMATOLOGY, set)
        Rating rate1 = new Rating("description", doctor, 1, number);
        Rating rate2 = new Rating("description", doctor, 2, 2);
        doctor.rating.add(rate1)
        doctor.rating.add(rate2)
        doctor.setDoctorId(1)
        List<Doctor> list = new ArrayList<>()
        list.add(doctor)
        doctorRepository.findAll() >> list
        doctorRepository.findById(1) >> Optional.of(doctor)
        when:
        service.setNewDoctorsRate()
        then:
        def result = doctor.averageOdRates
        result == expectedResult
        where:
        number || expectedResult
        1      || 1.5
        2      || 2.0
        5      || 3.5

    }

    def "checkIfRateIsBetween_1_and_5_returns true if is in range"() {
        when:
        def result = service.checkIfRateIsBetween_1_and_5(number)
        then:
        result == expectedResult
        where:
        number || expectedResult
        1      || true
        2      || true
        5      || true
    }

    def "checkIfRateIsBetween_1_and_5 returns IllegalArgumentException for number 0"() {
        given:
        int number = 0
        when:
        service.checkIfRateIsBetween_1_and_5(number)
        then:
        thrown(IllegalArgumentException)
    }

    def "checkIfRateIsBetween_1_and_5 returns IllegalArgumentException for number 6"() {
        given:
        int number = 6
        when:
        service.checkIfRateIsBetween_1_and_5(number)
        then:
        thrown(IllegalArgumentException)
    }

    def "checkIfAlreadyExistRateForAppointment returns IllegalArgumentException if rate exist"() {
        given:
        ratingRepository.existsByAppointmentId(1) >> true
        when:
        def result = service.checkIfAlreadyExistRateForAppointment(1)
        then:
        thrown(IllegalArgumentException)
    }

    def "checkIfAlreadyExistRateForAppointment returns false if appointment doesnt exist"() {
        given:
        ratingRepository.existsByAppointmentId(1) >> false
        when:
        def result = service.checkIfAlreadyExistRateForAppointment(1)
        then:
        result == false
    }


    def "checkIfPatientIsAllowedToAddRate returns false if appointment done is false"() {
        given:
        appointmentRepository.existsAppointmentByIdAndDoneIsTrue(1)>> false
        when:
        def result = service.checkIfPatientIsAllowedToAddRate(1)
        then:
        thrown(IllegalArgumentException)
    }

    def "checkIfPatientIsAllowedToAddRate returns true if appointment done is done"() {
        given:
        appointmentRepository.existsAppointmentByIdAndDoneIsTrue(1)>>true
        when:
        def result = service.checkIfPatientIsAllowedToAddRate(1)
        then:
        result == true
    }


}
