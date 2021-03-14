package com.milka.DoctorAppointment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String description;
    @ManyToOne
    @JoinColumn(name = "doctorId")
    @JsonBackReference
    Doctor doctor;
    int patientId;
    @Min(1)
    @Max(5)
    int rate;

    public Rating() {
    }

    public Rating(String description, Doctor doctor, int patientId, @Min(1) @Max(5) int rate) {
        this.description = description;
        this.doctor = doctor;
        this.patientId = patientId;
        this.rate = rate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
