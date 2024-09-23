package com.dnd.game.controllers;

import com.dnd.game.dto.SongDTO;
import com.dnd.game.entities.DifficultyUsers;
import com.dnd.game.entities.Song;
import com.dnd.game.entities.UserAccount;
import com.dnd.game.services.AccountService;
import com.dnd.game.services.DifficultyService;
import com.dnd.game.services.SongService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final SongService songService;
    private final DifficultyService difficultyService;
    private final Path fileLocation = Paths.get("src/main/resources/static/images/profiles/");

    public AccountController(AccountService accountService, SongService songService, DifficultyService difficultyService) {
        this.accountService = accountService;
        this.songService = songService;
        this.difficultyService = difficultyService;
    }

    @PostMapping("/delete-song/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String getAccount(Authentication authentication, Model model, HttpServletRequest request) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<SongDTO> songs = songService.getSongsFromUser(authentication.getName());
            List<SongDTO> diffSongs = songService.getDifficultySongsFromUser(authentication.getName());
            List<DifficultyUsers> difficulties = difficultyService.getDiffFromUser(authentication.getName());
            UserAccount user = accountService.getUser(authentication.getName());
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("csrf", csrfToken);
            model.addAttribute("songs", songs);
            model.addAttribute("diffSongs", diffSongs);
            model.addAttribute("diff", difficulties);
            model.addAttribute("user", authentication.getName());
            model.addAttribute("authority", authentication.getAuthorities());

            if (user.getImagePath() == null) {
                user.setImagePath("/images/account.png");
            }
            model.addAttribute("account", user);
            return "account";
        }
        return "redirect:/login";
    }

    @PostMapping("/account")
    public String createSong(@RequestParam("imageProfile") MultipartFile file, Authentication authentication) {
        try {
            Path targetLocation = this.fileLocation.resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Cant store file");
        }
        accountService.saveImage(file.getOriginalFilename(), authentication.getName());

        return "redirect:/";
    }

}
