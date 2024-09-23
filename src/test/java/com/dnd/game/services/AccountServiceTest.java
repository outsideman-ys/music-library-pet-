package com.dnd.game.services;

import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    AccountService service;
    @Mock
    UserRepo userRepo;

    @BeforeEach
    void setUp() {
        this.service = new AccountService(userRepo);
    }

    @Test
    void getUserMustWorkFine() {
        UserAccount user = new UserAccount("user", "password", "user@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER"));

        when(userRepo.findByUsername("user")).thenReturn(user);
        when(userRepo.findByEmail("user@gmail.com")).thenReturn(user);

        UserAccount userName = service.getUser("user");
        UserAccount userEmail = service.getUserByEmail("user@gmail.com");

        assertThat(userName.getUsername()).isEqualTo("user");
        assertThat(userEmail.getEmail()).isEqualTo("user@gmail.com");
    }

    @Test
    void compareUsersReturnTrueCorrectly() {
        UserAccount user1 = new UserAccount("user", "password", "user@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER"));
        UserAccount user2 = new UserAccount("bob", "password", "user@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER"));

        when(userRepo.findByUsername("user")).thenReturn(null);
        when(userRepo.findByEmail("user@gmail.com")).thenReturn(user2);

        assertThat(service.compareUsers(user1)).isEqualTo(true);
    }

    @Test
    void compareUsersReturnFalseCorrectly() {
        UserAccount user1 = new UserAccount("user", "password", "user@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER"));
        UserAccount user2 = new UserAccount("bob", "password", "bob@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER"));

        when(userRepo.findByUsername("bob")).thenReturn(null);
        when(userRepo.findByEmail("bob@gmail.com")).thenReturn(null);

        assertThat(service.compareUsers(user2)).isEqualTo(false);
    }

    @Test
    void createNewUserMustReturnTheSameData() {
        given(userRepo.save(any(UserAccount.class)))
                .willReturn(new UserAccount("user", "password", "user@gmail.com",
                        "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER")));

        UserAccount user = service.saveUser(new UserAccount("user", "password", "user@gmail.com",
                "images/profiles/1.jpg", new SimpleGrantedAuthority("ROLE_USER")));


        assertThat(user.getEmail()).isEqualTo("user@gmail.com");
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo("password");

    }

    @Test
    void saveImageMustWorksFine() {

        UserAccount user = new UserAccount("bob", "password", "bob@gmail.com",
                "1.png", new SimpleGrantedAuthority("ROLE_USER"));


        when(userRepo.findByUsername("bob")).thenReturn(user);

        service.saveImage("2.png", "bob");

        verify(userRepo).findByUsername("bob");
        verify(userRepo).save(user);

    }

}
