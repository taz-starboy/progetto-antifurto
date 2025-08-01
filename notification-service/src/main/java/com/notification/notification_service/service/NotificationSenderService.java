package com.notification.notification_service.service;

import com.notification.notification_service.dto.NotificationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationSenderService {

    public String sendNotification(NotificationRequest request) {

        if (request.getDestinationType().equals("email")) {
            return sendEmail(request.getDestination());
        }
        return sendWhatsApp(request.getDestination());
    }

    private String sendWhatsApp(@NotBlank String destination) {
        return "whatsapp sent";
    }

    private String sendEmail(@NotBlank String destination) {
        return "email sent";
    }
}
