package com.notification.notification_service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.notification_service.dto.AiRequest;
import com.notification.notification_service.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class AiClient {

    @Value("${openrouter.api.url}")
    private String apiUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.ai.model}")
    private String aiModel;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();


    public String getAiNotification(NotificationRequest request) {

        final String DEFAULT_MESSAGE = "Attività rilevata in " + request.getLocation() + " controlla la situazione.";

        /* SET HEADERS */
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        /* SET BODY REQUEST INTO DTO */
        AiRequest requestBody = generateRequestBody(request);

        try {
            /* convert DTO into json */
            String bodyJson = mapper.writeValueAsString(requestBody);

            /* set http entity */
            HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return extractContentFromResponse(response.getBody());
            }
            log.warn("\nRequest to OpenRouter failed with status: {}", response.getStatusCode());

        } catch (JsonProcessingException | RestClientException e) {
            log.error("\nCouldn't get response: {}", e.getMessage());
        }
        return DEFAULT_MESSAGE;
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
