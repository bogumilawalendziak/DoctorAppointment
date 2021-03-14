package com.milka.DoctorAppointment.model;

public class ReadRateDTO {

    String description;
    String patientName;
    int rate;


    public ReadRateDTO(Rating rate) {
        this.description = rate.getDescription();
        this.rate = rate.getRate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
