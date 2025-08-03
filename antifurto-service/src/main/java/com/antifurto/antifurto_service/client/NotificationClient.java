package com.antifurto.antifurto_service.client;

import com.antifurto.antifurto_service.dto.HttpAntifurtoResponse;
import com.antifurto.antifurto_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${notification.service.url}")
    private String notificationUrl;


    public ResponseEntity<HttpAntifurtoResponse.Notification> sendNotification(NotificationRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);

        try {
            return restTemplate.postForEntity(notificationUrl, entity, HttpAntifurtoResponse.Notification.class);

        } catch (RestClientException e) {
            log.error("\nError while trying to send request to Notification-Service: {}", e.getMessage());
            return ResponseEntity.badRequest().body(
                    HttpAntifurtoResponse.Notification.builder()
                            .localDateTime(LocalDateTime.now())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .generatedByAi(false)
                            .sentToClient(false)
                            .generatedNotification(null)
                            .error(e.getMessage()).build()
            );
        }

    }
}
