
//DON'T NEED IT
/*package com.dnd.game.repositories;

import com.dnd.game.entities.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepoH2Test {

    @Autowired
    UserRepo userRepo;

    @BeforeEach
    void setUp() {
        userRepo.saveAll(List.of(new UserAccount("bob", "password",
                "bob@gmail.com", "1.png", new SimpleGrantedAuthority("ROLE_USER")),
                new UserAccount("user", "password",
                        "user@gmail.com", "1.png", new SimpleGrantedAuthority("ROLE_USER")),
                new UserAccount("alice", "password",
                        "alice@gmail.com", "1.png", new SimpleGrantedAuthority("ROLE_USER"))));
    }


    @Test
    void findAllShouldProduceAllUsers() {
        List<UserAccount> users = userRepo.findAll();
        assertThat(users).hasSize(3);
    }

    @Test
    void findUsernameShouldRetrieveOneEntry() {
        List<UserAccount> users = new ArrayList<>();
        users.add(userRepo.findByUsername("bob"));
        assertThat(users).hasSize(1);
        assertThat(users).extracting(UserAccount::getUsername)
                .containsExactlyInAnyOrder("bob");
    }




}*/
