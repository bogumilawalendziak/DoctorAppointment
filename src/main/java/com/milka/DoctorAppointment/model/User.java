package com.milka.DoctorAppointment.model;

import javax.persistence.Embeddable;

@Embeddable
public class User {

    String name;
    String surname;
    Position Person;


    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Position getPerson() {
        return Person;
    }

    public void setPerson(Position person) {
        Person = person;
    }
}
