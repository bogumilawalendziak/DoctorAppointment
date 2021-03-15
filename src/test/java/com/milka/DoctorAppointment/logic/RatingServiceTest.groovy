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
        Rating rate1 = new Rating("description", doctor, 1, 2);
        Rating rate2 = new Rating("description", doctor, 2, 2);
        doctor.rating.add(rate1)
        doctor.rating.add(rate2)
        doctor.setDoctorId(1)

        doctorRepository.findById(1) >> Optional.of(doctor)
        when:
        def result = service.calculateRatingAverage(doctor.doctorId)
        then:
        result ==2.0

    }

    def "setNewDoctorsRate is setting average of ratings"() {
        given:
        Set<Rating> set = new HashSet<>()
        Doctor doctor = new Doctor(new User(), Specialization.DERMATOLOGY, set)
        Rating rate1 = new Rating("description", doctor, 1, 2);
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
        doctor.averageOdRates==2.0
    }
}
