package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    AppointmentRepository appointmentRepository;
    DoctorRepository doctorRepository;
    RatingRepository ratingRepository;


    public RatingService(AppointmentRepository appointmentRepository,
                         DoctorRepository doctorRepository, RatingRepository ratingRepository) {
        this.appointmentRepository = appointmentRepository;

        this.doctorRepository = doctorRepository;
        this.ratingRepository = ratingRepository;

    }

    public void createRate(WriteRateDTO toCreate) {

        int patientId = appointmentRepository.findById(toCreate.getAppointmentId()).getPatient().getPatientId();
        int doctorId = appointmentRepository.findById(toCreate.getAppointmentId()).getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId).get();
        int rate = toCreate.getRate();
        String description = toCreate.getDescription();

        if (!checkIfAlreadyExistRateForAppointment(toCreate.getAppointmentId()) && checkIfPatientIsAllowedToAddRate(toCreate.getAppointmentId())
                && checkIfRateIsBetween_1_and_5(rate)) {
            Rating rating = new Rating(description, doctor, patientId, rate);
            ratingRepository.save(rating);
            setNewDoctorsRate();
        }

    }


    public boolean checkIfPatientIsAllowedToAddRate(int appointmentId) {

        if (!appointmentRepository.existsAppointmentByIdAndDoneIsTrue(appointmentId)) {
            throw new IllegalArgumentException("Wizyta jeszcze się nie odbyła.");
        } else return true;
    }

    public boolean checkIfAlreadyExistRateForAppointment(int appointmentId) {

        if (ratingRepository.existsByAppointmentId(appointmentId)) {
            throw new IllegalArgumentException("Istnieje już ocena dla tej wizyty.");
        } else return false;
    }

    public boolean checkIfRateIsBetween_1_and_5(int rate) {
        if (!(rate >= 1 && rate <= 5)) {
            throw new IllegalArgumentException("Ocena może być z zakresu 1-5.");
        } else return true;
    }


    double calculateRatingAverage(int doctorId) {
        List<Integer> doctorsRate = doctorRepository.findById(doctorId).map(Doctor::getRating).get()
                .stream().map(Rating::getRate).collect(Collectors.toList());
        int quantityOfRates = doctorsRate.size();
        int sumOfRates = doctorsRate.stream().mapToInt(i -> i).sum();
        return sumOfRates / quantityOfRates;
    }

    void setNewDoctorsRate() {
        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(doctor -> doctor.setAverageOdRates(calculateRatingAverage(doctor.getDoctorId())));
    }
}

