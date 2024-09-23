package com.dnd.game.controllers;

import com.dnd.game.dto.SongDTO;
import com.dnd.game.entities.Song;
import com.dnd.game.services.DifficultyService;
import com.dnd.game.services.MusicxmatchService;
import com.dnd.game.services.SongService;
import com.dnd.game.services.YoutubeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SongController {

    private final SongService songService;
    private final DifficultyService difficultyService;

    public SongController(SongService songService, DifficultyService difficultyService) {
        this.songService = songService;
        this.difficultyService = difficultyService;
    }

    @GetMapping("/songs/{id}")
    public String getSongById(@PathVariable("id") Long id, Model model) {
        try {
            SongDTO song = songService.findById(id);
            model.addAttribute("song", song);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred: " + e.getMessage());
        }
        return "song";
    }

    @PostMapping("/songs/{id}")
    public String setRating(@PathVariable("id") Long id, @RequestParam Integer difficulty, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            difficultyService.saveUserSong(id, difficulty, authentication);
            songService.updateDifficulty(id);
            return "redirect:/songs/"+ id;
        }
        else return "login";
    }

    record DifficultyRequest(Integer difficulty) {}

}
