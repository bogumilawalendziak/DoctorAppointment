package com.milka.DoctorAppointment.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int doctorId;
    @Embedded
    private User user = new User(Position.DOCTOR);
    private Specialization specialization;
    @OneToMany
    @JsonManagedReference
    private Set<Rating> rating = new HashSet<>();
    double averageOdRates;

    public Doctor() {
    }

    public Doctor(User user, Specialization specialization, Set<Rating> rating) {
        this.user = user;
        this.specialization = specialization;
        this.rating = rating;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Set<Rating> getRating() {
        return rating;
    }

    public void setRating(Set<Rating> rating) {
        this.rating = rating;
    }

    public double getAverageOdRates() {
        return averageOdRates;
    }

    public void setAverageOdRates(double averageOdRates) {
        this.averageOdRates = averageOdRates;
    }
}
