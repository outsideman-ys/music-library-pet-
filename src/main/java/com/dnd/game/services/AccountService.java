package com.dnd.game.services;

import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccountService {

    private final UserRepo userRepo;

    public AccountService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserAccount saveUser(UserAccount user) {
        return userRepo.save(user);
    }

    public boolean compareUsers(UserAccount user) {
        return userRepo.findByUsername(user.getUsername()) != null || userRepo.findByEmail(user.getEmail()) != null;
    }

    public UserAccount getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public UserAccount getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void saveImage(String fileName, String username) {
        String imagePath = "images/profiles/" + fileName;
        UserAccount user = userRepo.findByUsername(username);
        user.setImagePath(imagePath);
        userRepo.save(user);
    }

}
