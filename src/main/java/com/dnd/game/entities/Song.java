package com.dnd.game.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="song_name")
    private String songName;
    @Column(name="band_name")
    private String bandName;
    @Column(name="album_name")
    private String albumName;
    @Column(name="rating")
    private int rating;
    @Column(name="img_path")
    private String imagePath;
    @CreationTimestamp
    @Column(name="created_date")
    private LocalDateTime createdDate;

    @Column(name="gtrpro_path")
    private String guitarProPath;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
