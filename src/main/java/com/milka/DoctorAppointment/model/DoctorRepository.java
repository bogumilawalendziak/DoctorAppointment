package com.milka.DoctorAppointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findAll();

    Optional<Doctor> findById(Integer integer);

    List<Doctor> findBySpecialization(Specialization specialization);
    //Doctor findFirstBySpecialization(Specialization specialization);

    Doctor save(Doctor doctor);
}
