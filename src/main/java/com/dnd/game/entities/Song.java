package com.dnd.game.entities;

import com.dnd.game.enums.DifficultyLevel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "song")
    Set<DifficultyUsers> userSongs;

    @Column(name="song_name")
    private String songName;

    @Column(name="band_name")
    private String bandName;

    @Column(name="album_name")
    private String albumName;

    @Column(name="difficulty")
    private Integer difficulty;

    @Column(name="username")
    private String username;

    @Column(name="img_path")
    private String imagePath;

    @CreationTimestamp
    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="gtrpro_path")
    private String guitarProPath;

    @Column(name="youtube_id")
    private String youtubeId;

    public Song() {
    }

    public Song(String songName, String bandName, String albumName, Integer difficulty,
                String username, String imagePath, LocalDateTime createdDate,
                String guitarProPath, String youtubeId) {
        this.songName = songName;
        this.bandName = bandName;
        this.albumName = albumName;
        this.difficulty = difficulty;
        this.username = username;
        this.imagePath = imagePath;
        this.createdDate = createdDate;
        this.guitarProPath = guitarProPath;
        this.youtubeId = youtubeId;
    }

    public Set<DifficultyUsers> getUserSongs() {
        return userSongs;
    }

    public void setUserSongs(Set<DifficultyUsers> userSongs) {
        this.userSongs = userSongs;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public DifficultyLevel getDifficultyAsString() {
        return DifficultyLevel.fromLevel(this.difficulty);
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGuitarProPath() {
        return guitarProPath;
    }

    public void setGuitarProPath(String guitarProPath) {
        this.guitarProPath = guitarProPath;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songName='" + songName + '\'' +
                ", bandName='" + bandName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", difficulty=" + difficulty +
                ", username='" + username + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", guitarProPath='" + guitarProPath + '\'' +
                ", youtubeId='" + youtubeId + '\'' +
                '}';
    }
}
