package com.dnd.game.services;

import com.dnd.game.dto.SongDTO;
import com.dnd.game.entities.DifficultyUsers;
import com.dnd.game.entities.Song;
import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.DifficultySongRepo;
import com.dnd.game.repositories.SongRepo;
import com.dnd.game.repositories.UserRepo;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepo songRepo;
    private final DifficultySongRepo difficultySongRepo;
    private final UserRepo userRepo;

    public SongService(SongRepo songRepo, DifficultySongRepo difficultySongRepo, UserRepo userRepo) {
        this.songRepo = songRepo;
        this.difficultySongRepo = difficultySongRepo;
        this.userRepo = userRepo;
    }

    public List<SongDTO> getDifficultySongsFromUser(String username) {
        UserAccount user = userRepo.findByUsername(username);
        List<DifficultyUsers> difficultyUsers = difficultySongRepo.findAllByUserId(user.getId());
        List<Song> songs = new ArrayList<>();
        for (DifficultyUsers diffUser : difficultyUsers) {
            Optional<Song> song = songRepo.findById(diffUser.getSong().getId());
            songs.add(song.get());
        }
        if (songs.isEmpty()) {
            return null;
        }
        else {
            return songs.stream()
                    .map(song -> new SongDTO(song, this))
                    .collect(Collectors.toList());
        }
    }

    public Page<Song> getAllSongs(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return songRepo.findAll(pageable);
    }

    public void updateDifficulty(Long songId) {
        List<DifficultyUsers> ratingUsers = difficultySongRepo.findAllBySongId(songId);
        int sum = 0;
        int size = ratingUsers.size();
        for (DifficultyUsers ratingUser : ratingUsers) {
            System.out.println(ratingUser.getDifficulty());
            sum += ratingUser.getDifficulty();
        }
        if (size == 0) size = 1;
        Optional<Song> optSong = songRepo.findById(songId);
        Song song = optSong.get();
        song.setDifficulty(sum/size);
        songRepo.save(song);
    }

    public List<SongDTO> getSongsFromUser(String username) {
        List<Song> songs = songRepo.findByUsername(username);
        return songs.stream()
                .map(song -> new SongDTO(song, this))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        songRepo.findById(id).map(songEntity -> {
            songRepo.delete(songEntity);
            return true;
        }).orElseThrow(() -> new RuntimeException("No song at " + id));
    }

    public SongDTO findById(Long id) {
        Optional<Song> song = songRepo.findById(id);
        if (song.isPresent()) {
            return new SongDTO(song.get(), this);
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

    public List<SongDTO> getRatingForHome() {
        List<Song> songs = songRepo.findTop5ByOrderByDifficultyDesc();
        return songs.stream()
                .map(song -> new SongDTO(song, this))
                .collect(Collectors.toList());
    }

    public List<SongDTO> getDateForHome() {
        List<Song> songs = songRepo.findTop5ByOrderByCreatedDateDesc();
        return songs.stream()
                .map(song -> new SongDTO(song, this))
                .collect(Collectors.toList());
    }

    public SongDTO saveSong(Song song, String username, String videoId, String fileName) {
        song.setDifficulty(0);
        song.setYoutubeId(videoId);
        song.setImagePath(getTheImagePath(song.getAlbumName()));
        song.setGuitarProPath(getGuitarProPath(fileName));
        song.setUsername(username);
        return new SongDTO(songRepo.save(song), this);
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

    private String getGuitarProPath(String fileName) {
        return "tabs/" + fileName;
    }

}
