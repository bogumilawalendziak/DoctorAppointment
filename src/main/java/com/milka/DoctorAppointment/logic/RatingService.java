package com.milka.DoctorAppointment.logic;

import com.milka.DoctorAppointment.model.*;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    AppointmentRepository appointmentRepository;
    PatientRepository patientRepository;
    DoctorRepository doctorRepository;

    public RatingService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public void createRate(WriteRateDTO toCreate){

        int patientId = appointmentRepository.findById(toCreate.getAppointmentId()).getPatient().getPatientId();
        int doctorId = appointmentRepository.findById(toCreate.getAppointmentId()).getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId).get();
        int rate = toCreate.getRate();
        String description = toCreate.getDescription();

        if(!checkIfAlreadyExistRateForAppointment(toCreate.getAppointmentId()) && checkIfPatientIsAllowedToAddRate(toCreate.getAppointmentId())
                && checkIfRateIsBetween_1_and_5(rate)){
            Rating rating = new Rating(description,doctor,patientId,rate);
        }

    }


    public boolean checkIfPatientIsAllowedToAddRate(int appointmentId){

        return true;
    }

    public boolean checkIfAlreadyExistRateForAppointment(int appointmentId){

        return true;
    }

    public boolean checkIfRateIsBetween_1_and_5(int rate){

        return true;
    }

}

