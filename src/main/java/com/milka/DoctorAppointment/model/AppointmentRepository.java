package com.milka.DoctorAppointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Override
    List<Appointment> findAll();

    @Override
    Optional<Appointment> findById(Integer integer);

    Appointment save(Appointment appointment);

    @Override
    void delete(Appointment appointment);
    void deleteAppointmentById(@Param("id") int Id);

    Optional<Appointment> getAllByPatientId(@Param("id") int Id);

    Optional<Doctor> getAllByDoctorId(@Param("id") int Id);

    List<Appointment> findByDateAAndPatientId(Date date, int Id);





}
