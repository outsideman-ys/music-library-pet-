package com.dnd.game.repositories;

import com.dnd.game.entities.DifficultyUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DifficultySongRepo extends JpaRepository<DifficultyUsers, Long> {

    Optional<DifficultyUsers> findBySongIdAndUserId(Long songId, Long userId);

    List<DifficultyUsers> findAllBySongId(Long songId);

    List<DifficultyUsers> findAllByUserId(Long userId);

}
