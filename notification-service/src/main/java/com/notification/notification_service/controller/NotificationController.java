package com.notification.notification_service.controller;

import com.notification.notification_service.dto.NotificationRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Value("${service.authorization.api.key}")
    private String authenticationKey;

    @PostMapping
    public ResponseEntity<String> getNotification(@RequestBody @Valid NotificationRequest request, @RequestHeader("X-API-KEY") String apiKey) {

        if (!authenticationKey.equals(apiKey)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // Simula gestione notifica (potresti loggare o stampare su console)
        log.info("Notifica ricevuta: sensorType={}, location={}, timeStamp={}", request.getSensorType(), request.getLocation(), request.getTimeStamp());
        //System.out.printf("Notifica ricevuta: tipo=%s, posizione=%s, orario=%s%n", request.getSensorType(), request.getLocation(), request.getTimeStamp());

        return ResponseEntity.ok("Notifica ricevuta con successo");
    }
}
