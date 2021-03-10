package com.milka.DoctorAppointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {


    List<Appointment> findAll();

    @Override
   Appointment save(Appointment entity);

    void delete(Appointment appointment);

    Appointment findById(int Id);

    void deleteAppointmentById(@Param("id") int Id);

    Optional<List<Appointment>> getAllByPatientPatientId(@Param("id") int id);


    Optional<List<Appointment>> getAllByDoctorIdAndDate(int doctorId, LocalDate date);

    List<Appointment> findByPatientPatientId(int Id);





}
