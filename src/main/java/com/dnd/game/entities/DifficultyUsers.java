package com.dnd.game.entities;

import jakarta.persistence.*;

@Table(name = "user_difficulty")
@Entity
public class DifficultyUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "difficulty_numb")
    private Integer difficultyNumb;

    public DifficultyUsers() {}

    public DifficultyUsers(UserAccount user, Song song, Integer difficulty) {
        this.user = user;
        this.song = song;
        this.difficultyNumb = difficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Integer getDifficulty() {
        return difficultyNumb;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficultyNumb = difficulty;
    }
}
