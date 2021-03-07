package com.milka.DoctorAppointment.controller;

import com.milka.DoctorAppointment.model.Appointment;
import com.milka.DoctorAppointment.model.AppointmentRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository repository;

    public AppointmentController(AppointmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    ResponseEntity getAllAppointments() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity getAllAppointmentById(@PathVariable int id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PostMapping
    ResponseEntity saveAppointment(@RequestBody Appointment toSave) {
        repository.save(toSave);
        return ResponseEntity.noContent().build();
    }

    //TODO toggle
    @PatchMapping("/{id}")
    ResponseEntity updateAppointment(@RequestBody Appointment toUpdate) {
        if (!repository.existsById(toUpdate.getId())) {
            throw new IllegalArgumentException();
        }
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteAppointmentById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException();
        }
        repository.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

}
