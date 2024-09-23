package com.dnd.game.repositories;

import com.dnd.game.entities.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = SongRepoTestcontainersTest.DataSourceInitializer.class)
public class SongRepoTestcontainersTest {

    @Autowired
    SongRepo songRepo;

    @Container
    static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.2")
            .withUsername("postgres");


    static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.
                    addInlinedPropertiesToEnvironment(applicationContext,
                            "spring.datasource.url=" + database.getJdbcUrl(),
                            "spring.datasource.username=" + database.getUsername(),
                            "spring.datasource.password=" + database.getPassword(),
                            "spring.jpa.hibernate.ddl-auto=create-drop");
        }
    }

    @BeforeEach
    void setUp() {
        songRepo.saveAll(List.of(new Song("Phase", "Breaking Benjamin", "Saturate", 2,
                        "bob", "1.png", LocalDateTime.now(),
                        "1.gp5", "youtubeId"),
                new Song("Anonymous", "Three Days Grace", "Three Days Grace", 1,
                        "bob", "2.png", LocalDateTime.now(),
                        "2.gp5", "ididid"),
                new Song("No Tired Of Winning", "Nothing More", "Spirits", 3,
                        "jack", "3.png", LocalDateTime.now(),
                        "3.gp5", "superid")));
    }


    @Test
    void findAllShouldProduceAllSongs() {
        List<Song> songs = songRepo.findAll();
        assertThat(songs).hasSize(3);
    }

    @Test
    void findByNameWorksFine() {
        Song song = songRepo.findBySongName("Anonymous");
        assertThat(song.getBandName()).isEqualTo("Three Days Grace");
    }

    @Test
    void findByUsernameWorksFine() {
        List<Song> songs = songRepo.findByUsername("bob");
        assertThat(songs).hasSize(2);
    }


}
