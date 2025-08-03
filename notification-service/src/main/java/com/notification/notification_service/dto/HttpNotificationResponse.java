package com.notification.notification_service.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class HttpNotificationResponse {

    private LocalDateTime localDateTime;
    private int status;
    private boolean generatedByAi;
    private boolean sentToClient;
    private String generatedNotification;
    private String error;
}
