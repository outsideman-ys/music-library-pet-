package com.dnd.game;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dnd.game.controllers.AccountController;
import com.dnd.game.entities.UserAccount;
import com.dnd.game.services.AccountService;
import com.dnd.game.services.DifficultyService;
import com.dnd.game.services.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AccountController.class)
public class SecurityBasedTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountService accountService;
    @MockBean
    SongService songService;
    @MockBean
    DifficultyService difficultyService;

    @BeforeEach
    void setup() {
        UserAccount mockUser = new UserAccount();
        mockUser.setImagePath("/1.png");
        mockUser.setEmail("bob@gmail.com");
        mockUser.setUsername("bob");

        UserAccount mockUser1 = new UserAccount();
        mockUser.setImagePath("/2.png");
        mockUser.setEmail("alice@gmail.com");
        mockUser.setUsername("alice");

        when(accountService.getUser("bob")).thenReturn(mockUser);
        when(accountService.getUser("alice")).thenReturn(mockUser1);
    }

    @Test
    void unauthUsersShouldNotAccessAccountPage() throws Exception {
        mvc.perform(get("/account"))
                .andExpect(status().is3xxRedirection()) //
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/google"));
    }

    @Test
    @WithMockUser(username="bob", roles = "USER")
    void authUsersShouldAccessAccountPage() throws Exception {
        mvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "alice", roles = "ADMIN")
    void adminShouldAccessHomePage() throws Exception {
        mvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    void newSongFromUnauthUserShouldFail() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "guitarProFile",
                "guitarProFile.txt",
                "text/plain",
                "Test content".getBytes()
        );

        mvc.perform(multipart("/all-songs")
                                .file(mockFile)
                                .param("songName", "song name")
                                .param("bandName", "band name")
                                .param("albumName", "album name")
                                .with(csrf())) //
                .andExpect(status().is3xxRedirection()) //
                .andExpect(redirectedUrl("http://localhost/oauth2/authorization/google"));
    }


}
