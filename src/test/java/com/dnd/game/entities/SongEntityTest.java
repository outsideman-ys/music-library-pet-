package com.dnd.game.entities;

import com.dnd.game.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SongEntityTest {

    @Test
    void newSongEntityShouldHaveNullId() {
        LocalDateTime time = LocalDateTime.now();
        Song song = new Song("Ultraviolet", "Silverstein", "Misery",
                0, "bob", "/images/misery.jpg", time,
                "/tabs/silverstein-misery.gp5", "youtubeId");
        assertThat(song.getId()).isNull();
        assertThat(song.getSongName()).isEqualTo("Ultraviolet");
        assertThat(song.getBandName()).isEqualTo("Silverstein");
        assertThat(song.getAlbumName()).isEqualTo("Misery");
        assertThat(song.getDifficulty()).isEqualTo(0);
        assertThat(song.getUsername()).isEqualTo("bob");
        assertThat(song.getImagePath()).isEqualTo("/images/misery.jpg");
        assertThat(song.getGuitarProPath()).isEqualTo("/tabs/silverstein-misery.gp5");
        assertThat(song.getYoutubeId()).isEqualTo("youtubeId");
        assertThat(song.getCreatedDate()).isEqualTo(time);
    }

    @Test
    void SongEntityToStringWorksCorrectly() {
        Song song = new Song("Ultraviolet", "Silverstein", "Misery",
                0, "bob", "/images/misery.jpg", LocalDateTime.now(),
                "/tabs/silverstein-misery.gp5", "youtubeId");
        assertThat(song.toString()).isEqualTo("Song{id=null, songName='Ultraviolet', bandName='Silverstein', " +
                "albumName='Misery', difficulty=0, username='bob', imagePath='/images/misery.jpg'" +
                ", guitarProPath='/tabs/silverstein-misery.gp5', youtubeId='youtubeId'}");
    }

    @Test
    void songEntitySetterWorksCorrectly() {
        LocalDateTime time = LocalDateTime.now();
        Song song = new Song("Ultraviolet", "Silverstein", "Misery",
                0, "bob", "/images/misery.jpg", LocalDateTime.now(),
                "/tabs/silverstein-misery.gp5", "youtubeId");

        song.setSongName("Reactor");
        song.setBandName("Billy Talent");
        song.setAlbumName("Crisis of Faith");
        song.setDifficulty(1);
        song.setYoutubeId("idYouTube");
        song.setImagePath("/images/crisis-of-faith.jpg");
        song.setGuitarProPath("/tabs/billy talent - reactor.gp5");
        song.setId(20L);
        song.setUsername("jack");
        song.setCreatedDate(time);

        assertThat(song.getId()).isEqualTo(20L);
        assertThat(song.getSongName()).isEqualTo("Reactor");
        assertThat(song.getBandName()).isEqualTo("Billy Talent");
        assertThat(song.getAlbumName()).isEqualTo("Crisis of Faith");
        assertThat(song.getDifficulty()).isEqualTo(1);
        assertThat(song.getUsername()).isEqualTo("jack");
        assertThat(song.getImagePath()).isEqualTo("/images/crisis-of-faith.jpg");
        assertThat(song.getGuitarProPath()).isEqualTo("/tabs/billy talent - reactor.gp5");
        assertThat(song.getYoutubeId()).isEqualTo("idYouTube");
        assertThat(song.getCreatedDate()).isEqualTo(time);
    }

    @Test
    void SongEntityDifficultyLevelMethodMustWorkCorrectly() {
        Song song = new Song();
        song.setDifficulty(1);
        assertThat(song.getSongName()).isEqualTo(null);
        assertThat(song.getDifficulty()).isEqualTo(1);
        assertThat(song.getDifficultyAsString()).isEqualTo(DifficultyLevel.BEGINNER);
    }

    @Test
    void SongEntityUsersSongSettersMustWorkCorrectly() {
        Song song = new Song();
        song.setUserSongs(null);
        assertThat(song.getUserSongs()).isEqualTo(null);
    }

}
