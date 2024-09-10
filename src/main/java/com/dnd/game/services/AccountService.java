package com.dnd.game.services;

import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserRepo userRepo;

    public AccountService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void saveUser(UserAccount user) {
        userRepo.save(user);
    }

    public boolean compareUsers(UserAccount user) {
        return userRepo.findByUsername(user.getUsername()) != null || userRepo.findByEmail(user.getEmail()) != null;
    }

}
