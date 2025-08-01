package com.antifurto.antifurto_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String sensorType;
    private String location;
    private LocalDateTime timeStamp;
    private String destinationType;
    private String destination;
}
