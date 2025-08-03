package com.notification.notification_service.controller;

import com.notification.notification_service.client.AiClient;
import com.notification.notification_service.dto.HttpNotificationResponse;
import com.notification.notification_service.dto.NotificationRequest;
import com.notification.notification_service.service.NotificationSenderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private AiClient aiClient;

    @Autowired
    private NotificationSenderService senderService;

    @PostMapping
    public ResponseEntity<HttpNotificationResponse> getNotificationRequest(@RequestBody @Valid NotificationRequest request) {

        HttpNotificationResponse responseEntity = aiClient.getAiNotification(request);
        boolean isSentToClient = senderService.sendNotificationToClient(request, responseEntity.getGeneratedNotification());
        responseEntity.setSentToClient(isSentToClient);

        HttpStatus httpStatus = isSentToClient ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(responseEntity);
    }
}
