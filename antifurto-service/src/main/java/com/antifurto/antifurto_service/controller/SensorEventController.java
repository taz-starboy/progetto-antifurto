package com.antifurto.antifurto_service.controller;

import com.antifurto.antifurto_service.dto.HttpAntifurtoResponse;
import com.antifurto.antifurto_service.dto.SensorEventRequest;
import com.antifurto.antifurto_service.model.SensorEvent;
import com.antifurto.antifurto_service.service.SensorEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class SensorEventController {

    private final SensorEventService service;


    @PostMapping
    public ResponseEntity<HttpAntifurtoResponse> createEvent(@RequestBody @Valid SensorEventRequest request) {

        HttpAntifurtoResponse httpAntifurtoResponse = service.saveEvent(request);
        return ResponseEntity.status(httpAntifurtoResponse.getHttpStatus()).body(httpAntifurtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<SensorEvent>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }
}