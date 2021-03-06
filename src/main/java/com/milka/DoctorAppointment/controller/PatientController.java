package com.milka.DoctorAppointment.controller;

import com.milka.DoctorAppointment.model.Patient;
import com.milka.DoctorAppointment.model.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    ResponseEntity<List<Patient>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Patient> findById(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    ResponseEntity<Patient> updatePatient(@RequestBody Patient patient, @PathVariable String id) {
        if (!repository.existsById(patient.getPatientId())) {
            throw new IllegalArgumentException();
        } else repository.save(patient);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        repository.save(patient);
        return ResponseEntity.noContent().build();
    }
}
