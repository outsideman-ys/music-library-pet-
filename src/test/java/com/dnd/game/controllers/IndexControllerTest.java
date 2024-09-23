package com.dnd.game.controllers;

import com.dnd.game.services.SongService;
import com.dnd.game.services.YoutubeService;
import com.dnd.game.entities.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(controllers = IndexController.class)
public class IndexControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongService songService;
    @MockBean
    YoutubeService youtubeService;

    @Test
    @WithMockUser
    void IndexPageHasLinksToOtherPages() throws Exception {
        String html = mockMvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(html).contains(
                "<a href=\"/\">Home</a>",
                "<a href=\"/all-songs\">Songs</a>",
                "<a href=\"/about\">About</a>");
    }


    @Test
    @WithMockUser
    void postNewSongShouldWork() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "guitarProFile",
                "guitarProFile.txt",
                "text/plain",
                "Test content".getBytes()
        );

        when(youtubeService.getVideoYouTube(anyString(), anyString()))
                .thenReturn(Mono.just("S9tKwSboJeg"));

        mockMvc.perform(
                multipart("/all-songs")
                        .file(mockFile)
                        .param("songName", "song name")
                        .param("albumName", "album name")
                        .param("bandName", "band name")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));


        verify(songService).saveSong(
            any(Song.class), eq("user"), eq("S9tKwSboJeg"), eq("guitarProFile.txt"));

    }


    @Test
    @WithMockUser
    void AboutLinkWorksFine() throws Exception {
        String html = mockMvc.perform(
                get("/about"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(html).contains(
                "<a href=\"/\">Home</a>",
                "<a href=\"/all-songs\">Songs</a>",
                "<a href=\"/about\">About</a>");

    }

}
