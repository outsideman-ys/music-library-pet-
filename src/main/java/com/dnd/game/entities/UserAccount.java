package com.dnd.game.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<DifficultyUsers> difficultyUsersSet;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="image_path")
    private String imagePath;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserAccount() {
    }

    public UserAccount(String username, String password, String email, String imagePath, SimpleGrantedAuthority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imagePath = imagePath;
        this.authorities.add(authority);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UserDetails asUser() {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return User.withUsername(getUsername())
                .password(passwordEncoder.encode(getPassword()))
                .authorities(getAuthorities())
                .build();
    }

    public Set<DifficultyUsers> getDifficultyUsersSet() {
        return difficultyUsersSet;
    }

    public void setDifficultyUsersSet(Set<DifficultyUsers> difficultyUsersSet) {
        this.difficultyUsersSet = difficultyUsersSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(SimpleGrantedAuthority authority) {
        this.authorities.add(authority);
    }
}
