package com.dnd.game.repositories;

import com.dnd.game.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepo extends JpaRepository<UserAccount, Long> {
}
