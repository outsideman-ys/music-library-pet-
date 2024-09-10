package com.dnd.game.repositories;

import com.dnd.game.entities.UserAccount;
import org.springframework.data.repository.Repository;

public interface UserRepo extends Repository<UserAccount, Long> {

    UserAccount findByUsername(String username);
    UserAccount findByEmail(String email);

    void save(UserAccount user);

}
