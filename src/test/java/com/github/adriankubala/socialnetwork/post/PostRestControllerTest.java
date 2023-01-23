package com.github.adriankubala.socialnetwork.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.github.adriankubala.socialnetwork.post.PostFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addPost() throws Exception {
        PostDto dto = new PostDto();
        dto.setPostDate(NEW_POST_POST_DATE);
        dto.setAuthor(NEW_POST_AUTHOR);
        dto.setContent(NEW_POST_CONTENT);
        dto.setViewCount(NEW_POST_VIEW_COUNT);

        mockMvc.perform(post("/posts")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void getTopTenMostViewedPosts() throws Exception {
        mockMvc.perform(get("/posts/top-ten"))
                .andExpect(status().isOk());
    }

    @Test
    void getPost() throws Exception {
        mockMvc.perform(get("/posts/" + POST_1_ID_AS_STRING))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(POST_1_ID_AS_STRING));
    }

    @Test
    void getNonExistingPost() throws Exception {
        mockMvc.perform(get("/posts/" + NON_EXISTING_POST_ID_AS_STRING))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Post with given id not found."));
    }

    @Test
    void editPost() throws Exception {
        PostDto dto = new PostDto();
        dto.setId(POST_1_ID);
        dto.setPostDate(POST_1_POST_DATE);
        dto.setAuthor(POST_1_AUTHOR);
        dto.setContent(NEW_POST_CONTENT);
        dto.setViewCount(POST_1_VIEW_COUNT);

        mockMvc.perform(put("/posts")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void editNonExistingPost() throws Exception {
        PostDto dto = new PostDto();
        dto.setId(NON_EXISTING_POST_ID);

        mockMvc.perform(put("/posts")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Post with given id not found."));
    }

    @Test
    void deletePost() throws Exception {
        mockMvc.perform(delete("/posts/" + POST_1_ID_AS_STRING))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNonExistingPost() throws Exception {
        mockMvc.perform(delete("/posts/" + NON_EXISTING_POST_ID_AS_STRING))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Post with given id not found."));
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
