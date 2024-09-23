package com.dnd.game.repositories;

import com.dnd.game.entities.UserAccount;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends Repository<UserAccount, Long> {

    UserAccount findByUsername(String username);
    UserAccount findByEmail(String email);

    UserAccount save(UserAccount user);

    Optional<UserAccount> findById(Long id);


}
