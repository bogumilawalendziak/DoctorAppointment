package com.milka.DoctorAppointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    boolean existsByAppointmentId(int appointmentId);
}
