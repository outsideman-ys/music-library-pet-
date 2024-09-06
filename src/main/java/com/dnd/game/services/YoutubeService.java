package com.dnd.game.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

@Service
public class YoutubeService {

    private final WebClient webClient;

    public YoutubeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.googleapis.com/youtube/v3/search/").build();
    }


    public Mono<String> getVideoYouTube(String query, String apiKey) {
        String url = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/youtube/v3/search/")
                .queryParam("part", "snippet")
                .queryParam("q", query)
                .queryParam("key", apiKey)
                .queryParam("type", "video")
                .queryParam("maxResults", 1)
                .build()
                .toUriString();

        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractVideoIdFromResponse);
    }

    private String extractVideoIdFromResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            return root.path("items").get(0).path("id").path("videoId").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
