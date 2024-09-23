package com.dnd.game.controllers;

import com.dnd.game.dto.SongDTO;
import com.dnd.game.entities.Song;
import com.dnd.game.services.SongService;
import com.dnd.game.services.YoutubeService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class IndexController {

    private final SongService songService;
    private final YoutubeService youtubeService;
    private final Path fileLocation = Paths.get("src/main/resources/static/tabs/");

    public IndexController(SongService songService, YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
        this.songService = songService;
        try {
            Files.createDirectories(this.fileLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        List<SongDTO> ratingSongs = songService.getRatingForHome();
        List<SongDTO> dateSongs = songService.getDateForHome();
        model.addAttribute("ratingSongs", ratingSongs);
        model.addAttribute("dateSongs", dateSongs);
        return "index";
    }

    @PostMapping("/all-songs")
    public String createSong(@ModelAttribute Song song, @RequestParam("guitarProFile")MultipartFile file,
                             Authentication authentication) {
        try {
            Path targetLocation = this.fileLocation.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Cant store file");
        }
        String videoId = youtubeService.getVideoYouTube("" + song.getSongName() +
                " " + song.getBandName() + " lyrics", "AIzaSyBjgbVCoXM00dZytHXVrYnpnkEJTdpqmS4").block();
        songService.saveSong(song, authentication.getName(), videoId, file.getOriginalFilename());

        return "redirect:/";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

}
