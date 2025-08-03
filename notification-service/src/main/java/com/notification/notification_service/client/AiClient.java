package com.notification.notification_service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.notification_service.dto.AiRequest;
import com.notification.notification_service.dto.HttpNotificationResponse;
import com.notification.notification_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiClient {

    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.ai.model}")
    private String aiModel;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();


    public HttpNotificationResponse getAiNotification(NotificationRequest request) {

        final String DEFAULT_MESSAGE = "\nDEFAULT MESSAGE: Attività rilevata in " + request.getLocation() + " controlla la situazione.";

        /* Build request for OpenRouter */
        /* set headers */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        /* set body */
        AiRequest requestBody = generateRequestBody(request);

        try {
            /* convert DTO into string */
            String bodyToString = mapper.writeValueAsString(requestBody);
            /* set http entity */
            HttpEntity<String> entity = new HttpEntity<>(bodyToString, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            /* if status OK - build response to send to antifurto service */
            if (response.getStatusCode().is2xxSuccessful()) {
                return buildHttpNotificationResponse(true, response, DEFAULT_MESSAGE, null);
            }

            /* if any other status besides OK - build response to send to antifurto service */
            log.warn("\nRequest to OpenRouter failed with status: {}", response.getStatusCode());
            return buildHttpNotificationResponse(false, response, DEFAULT_MESSAGE, null);

        } catch (JsonProcessingException | RestClientException e) {
            log.error("\nCouldn't get response: {}", e.getMessage());
            return buildHttpNotificationResponse(false, null, DEFAULT_MESSAGE, e);
        }
    }


    private HttpNotificationResponse buildHttpNotificationResponse(boolean isGeneratedByAI, ResponseEntity<String> response, String defaultMessage, Exception exception) {
        HttpNotificationResponse hnr = new HttpNotificationResponse();

        hnr.setLocalDateTime(LocalDateTime.now());
        hnr.setError(exception != null ? exception.getMessage() : (isGeneratedByAI ? null : "Request to OpenRouter failed"));

        /* in case OK or open router server down */
        if (response != null) {
            hnr.setStatus(response.getStatusCode().value());
            hnr.setGeneratedByAi(isGeneratedByAI);
            try {
                hnr.setGeneratedNotification(extractContentFromResponse(response.getBody()));
            } catch (JsonProcessingException e) {
                log.error("\nCouldn't extract generated ai from open router response: {}", e.getMessage());
                hnr.setGeneratedNotification(defaultMessage);
            }
            return hnr;
        }

        /* in case of internal exception */
        hnr.setStatus(HttpStatus.BAD_REQUEST.value());
        hnr.setGeneratedByAi(isGeneratedByAI);
        hnr.setGeneratedNotification(defaultMessage);
        return hnr;
    }

    private String extractContentFromResponse(String body) throws JsonProcessingException {
        JsonNode root = mapper.readTree(body);
        return root.get("choices").get(0).get("message").get("content").asText();
    }

    private AiRequest generateRequestBody(NotificationRequest request) {

        String systemPrompt = "Sei un assistente che genera brevi notifiche antifurto (massimo 500 caratteri).";
        String userPrompt = "Genera una notifica per: " + request.getSensorType() + " rilevato in " + request.getLocation() + " alle " + request.getTimeStamp();

        AiRequest.Message systemMessage = new AiRequest.Message("system", systemPrompt);
        AiRequest.Message userMessage = new AiRequest.Message("user", userPrompt);

        AiRequest dto = new AiRequest();
        dto.setModel(aiModel);
        dto.setMessages(List.of(systemMessage, userMessage));
        dto.setMaxTokens(150);

        return dto;
    }
}
