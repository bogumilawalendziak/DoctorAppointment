package com.milka.DoctorAppointment.controller;

import com.milka.DoctorAppointment.logic.AppointmentService;
import com.milka.DoctorAppointment.model.Appointment;
import com.milka.DoctorAppointment.model.AppointmentDTO;
import com.milka.DoctorAppointment.model.AppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository repository;
    private final AppointmentService service;

    public AppointmentController(AppointmentRepository repository, AppointmentService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity getAllAppointmentById(@PathVariable int id) {
        return ResponseEntity.ok(repository.findById(id));
    }


    @GetMapping("/patient/{id}")
    ResponseEntity<List<Appointment>> getAllAppointmentByPatientId(@PathVariable int id) {
        return repository.getAllByPatientPatientId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity saveAppointment(@RequestBody AppointmentDTO toSave) {
        service.createAppointment(toSave);
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
