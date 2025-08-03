package com.antifurto.antifurto_service.service;

import com.antifurto.antifurto_service.client.NotificationClient;
import com.antifurto.antifurto_service.dto.HttpAntifurtoResponse;
import com.antifurto.antifurto_service.dto.NotificationRequest;
import com.antifurto.antifurto_service.dto.SensorEventRequest;
import com.antifurto.antifurto_service.model.SensorEvent;
import com.antifurto.antifurto_service.repository.SensorEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorEventService {

    private final SensorEventRepository repository;
    private final NotificationClient notificationClient;

    public HttpAntifurtoResponse saveEvent(SensorEventRequest request) {

        // create SensorEvent entity and save it to db
        SensorEvent sensorEvent = buildSensorEventEntity(request);
        SensorEvent entitySaved = repository.save(sensorEvent);

        // send request to notification-service
        NotificationRequest notificationRequest = buildNotificationRequest(request);
        HttpAntifurtoResponse.Notification notification = notificationClient.sendNotification(notificationRequest).getBody();

        /* build response */
        final HttpAntifurtoResponse httpAntifurtoResponse = new HttpAntifurtoResponse();
        httpAntifurtoResponse.setLocalDateTime(LocalDateTime.now());
        httpAntifurtoResponse.setHttpStatus(HttpStatus.CREATED.value());
        httpAntifurtoResponse.setSensorEvent(entitySaved);
        httpAntifurtoResponse.setNotification(notification);

        return httpAntifurtoResponse;
    }

    public List<SensorEvent> getAllEvents() {
        return repository.findAll();
    }


    private SensorEvent buildSensorEventEntity(SensorEventRequest request) {
        SensorEvent se = new SensorEvent();
        se.setSensorType(request.getSensorType());
        se.setLocation(request.getLocation());
        se.setTimeStamp(request.getTimeStamp());
        se.setDestinationType(request.getDestinationType());
        se.setDestination(request.getDestination());
        return se;
    }

    private NotificationRequest buildNotificationRequest(SensorEventRequest request) {
        NotificationRequest nr = new NotificationRequest();
        nr.setSensorType(request.getSensorType());
        nr.setLocation(request.getLocation());
        nr.setTimeStamp(request.getTimeStamp());
        nr.setDestinationType(request.getDestinationType());
        nr.setDestination(request.getDestination());
        return nr;
    }

}