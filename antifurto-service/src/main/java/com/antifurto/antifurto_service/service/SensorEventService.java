package com.antifurto.antifurto_service.service;

import com.antifurto.antifurto_service.client.NotificationClient;
import com.antifurto.antifurto_service.dto.NotificationRequest;
import com.antifurto.antifurto_service.dto.SensorEventRequest;
import com.antifurto.antifurto_service.model.SensorEvent;
import com.antifurto.antifurto_service.repository.SensorEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorEventService {

    private final SensorEventRepository repository;
    private final NotificationClient notificationClient;

    public SensorEvent saveEvent(SensorEventRequest request) {

        // try to call notification-service
        try {
            notificationClient.sendNotification(new NotificationRequest(request.getSensorType(), request.getLocation(), request.getTimeStamp()));
        } catch (Exception e) {
            log.warn("Notifica non inviata: {}", e.getMessage());
        }

        // create SensorEvent and save to db
        SensorEvent event = new SensorEvent();
        event.setSensorType(request.getSensorType());
        event.setLocation(request.getLocation());
        event.setTimeStamp(request.getTimeStamp());

        return repository.save(event);
    }

    public List<SensorEvent> getAllEvents() {
        return repository.findAll();
    }
}
/*
* @Service (di Spring)
    È un'annotazione che indica che una classe è un "servizio" nel contesto dell'applicazione Spring.
    Applica a una classe che contiene la logica di business, come il salvataggio, elaborazione o gestione di dati.
    Spring la registra automaticamente nel suo contenitore di oggetti (ApplicationContext).
    Può essere iniettata in altre classi (es. controller, altri servizi) tramite dependency injection.

* @RequiredArgsConstructor (di Lombok)
    * È un’annotazione di Lombok che genera automaticamente un costruttore con tutti i campi final (o @NonNull) della classe.
    * Ti evita di scrivere il costruttore manualmente.
    * È perfetta per l’iniezione di dipendenze via costruttore, che è la modalità consigliata in Spring.
*/