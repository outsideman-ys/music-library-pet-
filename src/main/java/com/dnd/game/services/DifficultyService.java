package com.dnd.game.services;

import com.dnd.game.entities.DifficultyUsers;
import com.dnd.game.entities.Song;
import com.dnd.game.entities.UserAccount;
import com.dnd.game.repositories.DifficultySongRepo;
import com.dnd.game.repositories.SongRepo;
import com.dnd.game.repositories.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DifficultyService {

    private final DifficultySongRepo difficultySongRepo;
    private final SongRepo songRepo;
    private final UserRepo userRepo;

    public DifficultyService(DifficultySongRepo difficultySongRepo, SongRepo songRepo, UserRepo userRepo) {
        this.difficultySongRepo = difficultySongRepo;
        this.songRepo = songRepo;
        this.userRepo = userRepo;
    }

    public DifficultyUsers saveUserSong(Long id, Integer difficulty, Authentication authentication) {
        Optional<Song> song = songRepo.findById(id);
        Optional<UserAccount> user = Optional.ofNullable(userRepo.findByUsername(authentication.getName()));
        Optional<DifficultyUsers> difficultyUser = difficultySongRepo.findBySongIdAndUserId(song.get().getId(), user.get().getId());
        DifficultyUsers difficultyUsers;
        if (difficultyUser.isEmpty()) {
            difficultyUsers = new DifficultyUsers(user.get(), song.get(), difficulty);
        }
        else {
            difficultyUsers = difficultyUser.get();
            difficultyUsers.setDifficulty(difficulty);
        }
        return difficultySongRepo.save(difficultyUsers);
    }

    public List<DifficultyUsers> getDiffFromUser(String username) {
        UserAccount userAccount = userRepo.findByUsername(username);
        List<DifficultyUsers> difficultyUsers = difficultySongRepo.findAllByUserId(userAccount.getId());
        return difficultyUsers;
    }

}
