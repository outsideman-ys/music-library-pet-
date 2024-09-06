package com.dnd.game.repositories;

import com.dnd.game.entities.Song;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepo extends JpaRepository<Song, Long> {

    List<Song> findTop5ByOrderByRatingDesc();
    List<Song> findTop5ByOrderByCreatedDateDesc();


    <S extends Song> Page<S> findAll(Example<S> example, Pageable pageable);

}
