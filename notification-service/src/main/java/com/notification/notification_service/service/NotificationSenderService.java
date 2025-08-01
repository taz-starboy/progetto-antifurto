package com.notification.notification_service.service;

import com.notification.notification_service.dto.NotificationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSenderService {

    @Value("${spring.mail.from}")
    private String mailFrom;

    private final JavaMailSender mailSender;

    public String sendNotification(NotificationRequest request, String notification) {

        if (request.getDestinationType().equals("email")) {
            return sendEmail(request.getDestination(), notification);
        }
        return sendWhatsApp(request.getDestination());
    }

    private String sendWhatsApp(@NotBlank String destination) {
        return "\nWhatsapp sender not yet available.";
    }

    private String sendEmail(@NotBlank String destination, String notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destination);
        message.setSubject("Notifica Antifurto");
        message.setText(notification);
        message.setFrom(this.mailFrom);

        try {
            mailSender.send(message);
            return "\nNotification Email SUCCESS.";
        } catch (MailException e) {
            log.error("\nSomething went wrong sending email: {}", e.getMessage());
            return "\nNotification Email FAILED.";
        }
    }
}
