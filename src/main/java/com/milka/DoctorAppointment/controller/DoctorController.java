package com.milka.DoctorAppointment.controller;

import com.milka.DoctorAppointment.logic.DoctorService;
import com.milka.DoctorAppointment.model.Doctor;
import com.milka.DoctorAppointment.model.DoctorRepository;
import com.milka.DoctorAppointment.model.Specialization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorRepository repository;
    private final DoctorService service;

    public DoctorController(DoctorRepository repository, DoctorService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    ResponseEntity<List<Doctor>> findAllDoctors() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Doctor> findDoctorById(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("specialization/{specialization}")
    ResponseEntity<List<Doctor>> findAvailableDoctorWithSpecialization(@PathVariable Specialization specialization) {
        return ResponseEntity.ok(repository.findBySpecialization(specialization));
    }

    @PostMapping
    ResponseEntity<Doctor> addDoctor(@RequestBody Doctor toSave) {
        toSave.setFree(true);
        repository.save(toSave);
        return ResponseEntity.noContent().build();
    }


}
