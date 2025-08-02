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

    @Value("${twilio.service.from.whatsapp}")
    private String whatsappFrom;

    @Value("${notification.service.tel.test}")
    private String testTel;

    private final JavaMailSender mailSender;


    public String sendNotification(NotificationRequest request, String notification) {

        if (request.getDestinationType().equals("email")) {
            return sendEmail(request.getDestination(), notification);
        }
        return sendWhatsApp(request.getDestination(), notification);
    }

    private String sendWhatsApp(@NotBlank String destination, String notification) {
        Twilio.init(this.twilioSID, this.twilioAuthToken);

        Message message = Message.creator(
                new PhoneNumber("whatsapp:" + this.testTel),
                new PhoneNumber(this.whatsappFrom),
                notification
        ).create();

        return "\nWhatsApp message sent: " + message.getSid();
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
