package com.milka.DoctorAppointment.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Embedded
    User user;
    Specialization specialization;
    List<Integer> workHours;
    boolean isFree;

    public Doctor() {
    }

    public List<Integer> getWorkHours() {
        return workHours;
    }

    public void setWorkHours(List<Integer> workHours) {
        this.workHours = workHours;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
