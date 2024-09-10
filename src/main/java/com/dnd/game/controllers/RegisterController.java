package com.dnd.game.controllers;

import com.dnd.game.entities.UserAccount;
import com.dnd.game.services.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new UserAccount());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserAccount user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        user.setAuthorities(new SimpleGrantedAuthority("ROLE_USER"));

        if (accountService.compareUsers(user)) {
            return "register";
        }

        accountService.saveUser(user);

        return "redirect:/login";
    }

}
