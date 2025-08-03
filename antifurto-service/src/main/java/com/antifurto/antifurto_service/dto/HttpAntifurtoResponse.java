package com.antifurto.antifurto_service.dto;

import com.antifurto.antifurto_service.model.SensorEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpAntifurtoResponse {

    private LocalDateTime localDateTime;
    private int httpStatus;
    private SensorEvent sensorEvent;
    private Notification notification;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Notification {
        private LocalDateTime localDateTime;
        private int status;
        private boolean generatedByAi;
        private boolean sentToClient;
        private String generatedNotification;
        private String error;

    }
}
