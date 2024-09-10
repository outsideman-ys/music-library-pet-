package com.dnd.game.controllers;

import com.dnd.game.entities.Song;
import com.dnd.game.services.MusicxmatchService;
import com.dnd.game.services.SongService;
import com.dnd.game.services.YoutubeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SongController {

    private final SongService songService;
    private final MusicxmatchService musicxmatchService;

    private final YoutubeService youtubeService;

    public SongController(SongService songService, MusicxmatchService musicxmatchService,
                          YoutubeService youtubeService) {
        this.songService = songService;
        this.musicxmatchService = musicxmatchService;
        this.youtubeService = youtubeService;
    }

    @GetMapping("/songs/{id}")
    public String getSongById(@PathVariable("id") Long id, Model model) {
        try {
            Song song = songService.findById(id);
            String videoId = youtubeService.getVideoYouTube("" + song.getSongName() +
                    " " + song.getBandName() + " lyrics", "AIzaSyBjgbVCoXM00dZytHXVrYnpnkEJTdpqmS4").block();

            model.addAttribute("song", song);
            model.addAttribute("videoId", videoId);
        } catch (Exception e) {
            e.printStackTrace(); // вывод в консоль для диагностики
            model.addAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "song";
    }

}
