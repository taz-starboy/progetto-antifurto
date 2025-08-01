package com.notification.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank
    private String sensorType;

    @NotBlank
    private String location;

    @NotNull
    private LocalDateTime timeStamp;

    @NotBlank
    private String destinationType;

    @NotBlank
    private String destination;
}
