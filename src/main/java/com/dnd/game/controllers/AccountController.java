package com.dnd.game.controllers;

import com.dnd.game.entities.Song;
import com.dnd.game.services.AccountService;
import com.dnd.game.services.SongService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final SongService songService;

    public AccountController(AccountService accountService, SongService songService) {
        this.accountService = accountService;
        this.songService = songService;
    }

    @PostMapping("/delete-song/{id}")
    public String deleteSong(@PathVariable Long id) {
        songService.delete(id);
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String getAccount(Authentication authentication, Model model, HttpServletRequest request) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<Song> songs = songService.getSongsFromUser(authentication.getName());
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            model.addAttribute("_csrf", csrfToken);
            model.addAttribute("songs", songs);
            model.addAttribute("user", authentication.getName());
            model.addAttribute("authority", authentication.getAuthorities());
            return "account";
        }
        return "redirect:/login";
    }

}
