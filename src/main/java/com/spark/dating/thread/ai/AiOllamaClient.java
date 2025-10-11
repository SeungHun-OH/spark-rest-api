package com.spark.dating.thread.ai;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
@Component
public class AiOllamaClient {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String generateText(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama3");
            body.put("prompt", prompt);
            body.put("stream", false); // 한 번에 전체 응답 받기

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    OLLAMA_API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class);

            return (String) response.getBody().get("response");

        } catch (Exception e) {
            e.printStackTrace();
            return "AiOllamaClient Error: " + e.getMessage();
        }
    }
}