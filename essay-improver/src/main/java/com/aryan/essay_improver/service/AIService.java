package com.aryan.essay_improver.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {

    // 🔐 API KEY FROM application.properties
    @Value("${groq.api.key}")
    private String API_KEY;

    public String improveEssay(String text) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = "https://api.groq.com/openai/v1/chat/completions";

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_KEY);

            // Request body
            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama3-8b-8192");

            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content",
                    "You are an AI essay improver.\n" +
                            "Return ONLY valid JSON in this format:\n" +
                            "{\n" +
                            "  \"corrected\": \"...\",\n" +
                            "  \"improved\": \"...\",\n" +
                            "  \"suggestions\": [\"...\"],\n" +
                            "  \"score\": 85\n" +
                            "}\n\n" +
                            "Essay:\n" + text
            );

            messages.add(message);
            body.put("messages", messages);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            // API call
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map responseBody = response.getBody();

            // ✅ SAFE CHECKS
            if (responseBody == null || !responseBody.containsKey("choices")) {
                return fallback("API failed");
            }

            List choices = (List) responseBody.get("choices");

            if (choices.isEmpty()) {
                return fallback("Empty response");
            }

            Map choice = (Map) choices.get(0);
            Map msg = (Map) choice.get("message");

            if (msg == null || !msg.containsKey("content")) {
                return fallback("Invalid response");
            }

            return msg.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return fallback(e.getMessage());
        }
    }

    // 🔥 fallback (never crash backend)
    private String fallback(String reason) {
        return "{\n" +
                "\"corrected\":\"Error\",\n" +
                "\"improved\":\"" + reason.replace("\"", "'") + "\",\n" +
                "\"suggestions\":[\"Try again later\"],\n" +
                "\"score\":0\n" +
                "}";
    }
}