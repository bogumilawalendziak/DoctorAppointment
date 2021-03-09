package com.milka.DoctorAppointment.model;


import java.time.LocalDate;


public class AppointmentDTO {

    Specialization specialization;
    String description;
    int patientId;
    LocalDate date;

    public AppointmentDTO(Specialization specialization, String description, int patientId, LocalDate date) {
        this.specialization = specialization;
        this.description = description;
        this.patientId = patientId;
        this.date = date;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}