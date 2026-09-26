package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private  String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApikey;



    public GeminiService() {
        this.webClient = WebClient.builder().build();
    }

    public String getAnswer(String question){
        Map<String ,Object> requestBody = Map.of("contents",new Object[]{Map.of("parts",new Object[]{
        Map.of("text", question)})
        });

        String response = webClient.post()
            .uri(UriComponentsBuilder.fromUriString(geminiApiUrl)
                .queryParam("key", geminiApikey)
                .build()
                .toUri())
                .header("content-type", "application/json")
                .bodyValue(requestBody)
            .retrieve()
            .onStatus(status -> status.isError(), clientResponse ->
                clientResponse.bodyToMono(String.class)
                    .map(errorBody -> new IllegalStateException(
                        "Gemini API returned " + clientResponse.statusCode()
                            + ": " + errorBody)))
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
