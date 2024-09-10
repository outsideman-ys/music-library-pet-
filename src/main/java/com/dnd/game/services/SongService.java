package com.dnd.game.services;

import com.dnd.game.entities.Song;
import com.dnd.game.repositories.SongRepo;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SongService {

    private final SongRepo songRepo;
    private final Random random = new Random();

    public SongService(SongRepo songRepo) {
        this.songRepo = songRepo;
    }

    public Page<Song> getAllSongs(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return songRepo.findAll(pageable);
    }

    public List<Song> getSongsFromUser(String username) {
        return songRepo.findByUsername(username);
    }

    public void delete(Long id) {
        songRepo.findById(id).map(songEntity -> {
            songRepo.delete(songEntity);
            return true;
        }).orElseThrow(() -> new RuntimeException("No song at " + id));
    }

    public Song findById(Long id) {
        Optional<Song> song = songRepo.findById(id);
        if (song.isPresent()) {
            return song.get();
        } else {
            return null; // NEED TO FIX
        }
    }

    public Page<Song> search(int page, int size, String search, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Song song = new Song();
        if (StringUtils.hasText(search)) {
            song.setSongName(search);
            song.setBandName(search);
            song.setAlbumName(search);
        }

        Example<Song> example = Example.of(song, ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return songRepo.findAll(example, pageable);
    }

    public List<Song> getRatingForHome() {
        return songRepo.findTop5ByOrderByRatingDesc();
    }

    public List<Song> getDateForHome() {
        return songRepo.findTop5ByOrderByCreatedDateDesc();
    }

    public Song saveSong(Song song, String username) {
        int rating = random.nextInt(98);
        song.setRating(rating);
        song.setImagePath(getTheImagePath(song.getAlbumName()));
        song.setGuitarProPath(getGuitarProPath(song.getBandName(), song.getSongName()));
        song.setUsername(username);
        return songRepo.save(song);
    }

    private String getTheImagePath(String album) {
        if (!album.contains(" ")) {
            return "images/" + album.toLowerCase() + ".jpg";
        }
        else {
            album = album.replaceAll(" ", "-");
            return "images/" + album.toLowerCase() + ".jpg";
        }
    }

    private String getGuitarProPath(String band, String song) {
        return "tabs/" + band + " - " + song + ".gp5";
    }

}
