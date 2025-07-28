package com.antifurto.antifurto_service.client;

import com.antifurto.antifurto_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${notification.service.url}")
    private String notificationUrl;

    @Value("${notification.service.api.key}")
    private String apiKey;

    public void sendNotification(NotificationRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(notificationUrl, entity, String.class);
            log.info(response.getBody());
            //System.out.println("Notifica inviata con risposta: " + response.getBody());
        } catch (Exception e) {
            log.error("Errore nell'invio della notifica: {}", e.getMessage());
            //System.err.println("Errore nell'invio della notifica: " + e.getMessage());
        }

    }
}
