package com.milka.DoctorAppointment.model;

import javax.persistence.Embeddable;

@Embeddable
public class User {

    String name;
    String surname;
    Position position;


    public User(Position position) {
        this.position=position;
    }

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
