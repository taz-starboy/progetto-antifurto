package com.notification.notification_service.controller;

import com.notification.notification_service.client.AiClient;
import com.notification.notification_service.dto.NotificationRequest;
import com.notification.notification_service.service.NotificationSenderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Value("${service.authorization.api.key}")
    private String authenticationKey;

    @Autowired
    private AiClient aiClient;

    @Autowired
    private NotificationSenderService senderService;

    @PostMapping
    public ResponseEntity<String> getNotificationRequest(@RequestBody @Valid NotificationRequest request, @RequestHeader("X-API-KEY") String apiKey) {

        if (!authenticationKey.equals(apiKey)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String generatedNotification = aiClient.getAiNotification(request);
        log.info("\nGenerated notification: {}", generatedNotification);

        return ResponseEntity.ok(senderService.sendNotification(request));
    }
}
