package com.dnd.game.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LyricsResponse {
    private Message message;

    // Getters and Setters


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message {
        private Header header;
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        // Getters and Setters
    }

    public static class Header {
        @JsonProperty("status_code")
        private int statusCode;
        @JsonProperty("execute_time")
        private double executeTime;

        // Getters and Setters

    }

    public static class Body {
        private Lyrics lyrics;

        // Getters and Setters

        public Lyrics getLyrics() {
            return lyrics;
        }

        public void setLyrics(Lyrics lyrics) {
            this.lyrics = lyrics;
        }
    }

    public static class Lyrics {
        @JsonProperty("lyrics_id")
        private long lyricsId;
        @JsonProperty("explicit")
        private int explicit;
        @JsonProperty("lyrics_body")
        private String lyricsBody;
        @JsonProperty("script_tracking_url")
        private String scriptTrackingUrl;
        @JsonProperty("pixel_tracking_url")
        private String pixelTrackingUrl;
        @JsonProperty("lyrics_copyright")
        private String lyricsCopyright;
        @JsonProperty("updated_time")
        private String updatedTime;

        public String getLyricsBody() {
            return lyricsBody;
        }

        public void setLyricsBody(String lyricsBody) {
            this.lyricsBody = lyricsBody;
        }

        // Getters and Setters
    }
}