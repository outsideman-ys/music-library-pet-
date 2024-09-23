package com.dnd.game.dto;


import com.dnd.game.entities.Song;
import com.dnd.game.services.SongService;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class SongDTO {

    private Long id;
    private String songName;
    private String bandName;
    private String albumName;
    private String difficulty;
    private String username;
    private String imagePath;
    private LocalDateTime createdDate;
    private String guitarProPath;
    private String youtubeId;
    private Integer ratingUsers;

    private Integer difficultyNumber;


    public SongDTO(Song song, SongService productService) {
        this.id = song.getId();
        this.difficulty = song.getDifficultyAsString().getDesc();
        this.songName = song.getSongName();
        this.bandName = song.getBandName();
        this.imagePath = song.getImagePath();
        this.albumName = song.getAlbumName();
        this.username = song.getUsername();
        this.guitarProPath = song.getGuitarProPath();
        this.youtubeId = song.getYoutubeId();
        this.createdDate = song.getCreatedDate();
        this.difficultyNumber = song.getDifficulty();
    }

    public Song toEntity() {
        Song song = new Song();
        song.setId(this.id);
        song.setSongName(this.songName);
        song.setBandName(this.bandName);
        song.setImagePath(this.imagePath);
        song.setAlbumName(this.albumName);
        song.setUsername(this.username);
        song.setDifficulty(this.difficultyNumber);
        song.setCreatedDate(this.createdDate);
        song.setYoutubeId(this.youtubeId);
        song.setGuitarProPath(this.guitarProPath);
        return song;
    }

    public Integer getDifficultyNumber() {
        return difficultyNumber;
    }

    public void setDifficultyNumber(Integer difficultyNumber) {
        this.difficultyNumber = difficultyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getGuitarProPath() {
        return guitarProPath;
    }

    public void setGuitarProPath(String guitarProPath) {
        this.guitarProPath = guitarProPath;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public Integer getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(Integer ratingUsers) {
        this.ratingUsers = ratingUsers;
    }
}
