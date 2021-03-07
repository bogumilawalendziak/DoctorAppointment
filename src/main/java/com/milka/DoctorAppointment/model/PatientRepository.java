package com.milka.DoctorAppointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {


    List<Patient> findAll();

    Optional<Patient> findById(Integer integer);

    Patient save(Patient patient);

}
