package com.antifurto.antifurto_service.controller;

import com.antifurto.antifurto_service.dto.SensorEventRequest;
import com.antifurto.antifurto_service.model.SensorEvent;
import com.antifurto.antifurto_service.service.SensorEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class SensorEventController {

    private final SensorEventService service;

    @PostMapping
    public ResponseEntity<SensorEvent> createEvent(@RequestBody @Valid SensorEventRequest request) {
        return ResponseEntity.ok(service.saveEvent(request));
    }

    @GetMapping
    public ResponseEntity<List<SensorEvent>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }
}
/*
* @RestController
    * Indica che la classe è un controller REST, cioè:
    * Espone endpoint HTTP (API).
    * I metodi restituiscono risposte JSON direttamente (non HTML).
    * Combina @Controller + @ResponseBody.
*
* @RequestMapping("/events")
    * Mappa la classe su un percorso base delle API, in questo caso /events
    * Tutti gli endpoint dentro il controller avranno l’URL base /events.
*
* @RequiredArgsConstructor (di Lombok)
    * Genera un costruttore automatico con tutti i campi final
    * Usata per evitare di scrivere il costruttore a mano.
    * È perfetta per l’iniezione via costruttore, come si fa in Spring moderno.
*
* @PostMapping
    * Mappa il metodo HTTP POST
    * Indica che il metodo gestisce una richiesta POST (per esempio per creare qualcosa).
*
* @RequestBody
    * Dice a Spring di:
    * Leggere il corpo della richiesta HTTP
    * Convertirlo automaticamente in un oggetto Java
    * È fondamentale per ricevere dati JSON nei controller.
*
* @Valid
    * Attiva la validazione automatica del @RequestBody
    * Controlla che i campi dell’oggetto siano validi (es. @NotBlank, @Min, ecc.)
    * Se i dati non sono validi, Spring restituisce errore 400 Bad Request con dettagli.
* */