package com.milka.DoctorAppointment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class WriteRateDTO {

    String description;
    int appointmentId;
    @Min(1)
    @Max(5)
    int rate;

    public WriteRateDTO(String description, int appointmentId, @Min(1) @Max(5) int rate) {
        this.description = description;
        this.appointmentId = appointmentId;
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
