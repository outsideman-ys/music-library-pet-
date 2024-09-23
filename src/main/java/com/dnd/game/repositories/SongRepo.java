package com.dnd.game.repositories;

import com.dnd.game.entities.Song;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SongRepo extends JpaRepository<Song, Long> {

    List<Song> findTop5ByOrderByDifficultyDesc();
    List<Song> findTop5ByOrderByCreatedDateDesc();

    Song findBySongName(String songName);

    @PreAuthorize("#entity.username == authentication.name")
    @Override
    void delete(Song song);

    List<Song> findByUsername(String username);
    <S extends Song> Page<S> findAll(Example<S> example, Pageable pageable);

}
