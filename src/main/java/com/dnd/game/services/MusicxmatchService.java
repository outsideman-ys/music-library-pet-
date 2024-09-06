package com.dnd.game.services;

import com.dnd.game.entities.LyricsResponse;
import com.dnd.game.entities.Song;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MusicxmatchService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(MusicxmatchService.class);

    public MusicxmatchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.musixmatch.com/ws/1.1/")
                .build();
    }

    public Mono<String> getLyrics(Song song, String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/matcher.lyrics.get")
                        .queryParam("q_track", song.getSongName())
                        .queryParam("q_artist", song.getBandName())
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> logger.info("Received response: {}", response))
                .flatMap(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        LyricsResponse lyricsResponse = objectMapper.readValue(response, LyricsResponse.class);
                        if (lyricsResponse != null && lyricsResponse.getMessage() != null &&
                                lyricsResponse.getMessage().getBody() != null &&
                                lyricsResponse.getMessage().getBody().getLyrics() != null) {
                            return Mono.just(lyricsResponse.getMessage().getBody().getLyrics().getLyricsBody());
                        } else {
                            return Mono.just("Lyrics not found");
                        }
                    } catch (Exception e) {
                        logger.error("Error parsing JSON response", e);
                        return Mono.just("Error parsing lyrics");
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Error fetching lyrics", e);
                    return Mono.just("Error fetching lyrics");
                });
    }

}
