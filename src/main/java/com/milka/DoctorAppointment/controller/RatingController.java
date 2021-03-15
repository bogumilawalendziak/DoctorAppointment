package com.milka.DoctorAppointment.controller;

import com.milka.DoctorAppointment.logic.RatingService;
import com.milka.DoctorAppointment.model.Rating;
import com.milka.DoctorAppointment.model.WriteRateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class RatingController {

    RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<Rating> addRate(@RequestBody WriteRateDTO rating) {
        service.createRate(rating);

        return ResponseEntity.noContent().build();

    }
}
