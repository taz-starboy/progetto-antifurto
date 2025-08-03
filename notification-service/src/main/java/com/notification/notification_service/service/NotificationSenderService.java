package com.notification.notification_service.service;

import com.notification.notification_service.dto.NotificationRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

    @Value("${twilio.service.account.sid}")
    private String twilioSID;

    @Value("${twilio.service.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.service.tel.from}")
    private String twilioTelFrom;

    private final JavaMailSender mailSender;


    public boolean sendNotificationToClient(NotificationRequest request, String notification) {

        return switch (request.getDestinationType()) {
            case "email" -> sendEmail(request.getDestination(), notification);
            case "whatsapp" -> sendWhatsApp(request.getDestination(), notification);
            default -> {
                log.error("\nDestination Type '{}' is not recognized.", request.getDestinationType());
                yield false;
            }
        };
    }

    private boolean sendWhatsApp(@NotBlank String destination, String notification) {

        try {
            Twilio.init(this.twilioSID, this.twilioAuthToken);

            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + destination),
                    new PhoneNumber("whatsapp:" + this.twilioTelFrom),
                    notification
            ).create();
            log.info("\nNotification Whatsapp SUCCEEDED");
            return true;

        } catch (Exception e) {
            log.error("\nAn exception occurred trying to send whatsapp message: {}", e.getMessage());
            return false;
        }

    }

    private boolean sendEmail(@NotBlank String destination, String notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destination);
        message.setSubject("Notifica Antifurto");
        message.setText(notification);
        message.setFrom(this.mailFrom);

        try {
            mailSender.send(message);
            log.info("\nNotification Email SUCCESS.");
            return true;
        } catch (MailException e) {
            log.error("\nAn exception occurred trying to send email: {}", e.getMessage());
            return false;
        }
    }
}
