package com.github.adriankubala.socialnetwork.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.adriankubala.socialnetwork.post.PostFixture.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void addPost() {
        PostDto dto = new PostDto();
        dto.setPostDate(NEW_POST_POST_DATE);
        dto.setAuthor(NEW_POST_AUTHOR);
        dto.setContent(NEW_POST_CONTENT);
        dto.setViewCount(NEW_POST_VIEW_COUNT);

        PostDto result = postService.addPost(dto);

        assertEquals(NEW_POST_ID, result.getId());
        assertEquals(NEW_POST_POST_DATE, result.getPostDate());
        assertEquals(NEW_POST_AUTHOR, result.getAuthor());
        assertEquals(NEW_POST_CONTENT, result.getContent());
        assertEquals(NEW_POST_VIEW_COUNT, result.getViewCount());
    }

    @Test
    void getAllPosts() {
        List<PostDto> result = postService.getAllPosts();

        assertEquals(15, result.size());
    }

    @Test
    void getTopTenMostViewedPosts() {
        List<PostDto> result = postService.getTopTenMostViewedPosts();

        assertEquals(10, result.size());
        assertEquals(15, result.get(0).getViewCount());
        assertEquals(14, result.get(1).getViewCount());
        assertEquals(13, result.get(2).getViewCount());
        assertEquals(12, result.get(3).getViewCount());
        assertEquals(11, result.get(4).getViewCount());
        assertEquals(10, result.get(5).getViewCount());
        assertEquals(9, result.get(6).getViewCount());
        assertEquals(8, result.get(7).getViewCount());
        assertEquals(7, result.get(8).getViewCount());
        assertEquals(6, result.get(9).getViewCount());
    }

    @Test
    void getPost() {
        PostDto result = postService.getPost(POST_1_ID);

        assertEquals(POST_1_ID, result.getId());
        assertEquals(POST_1_POST_DATE, result.getPostDate());
        assertEquals(POST_1_AUTHOR, result.getAuthor());
        assertEquals(POST_1_CONTENT, result.getContent());
        assertEquals(POST_1_VIEW_COUNT, result.getViewCount());
    }

    @Test
    void getNonExistingPost() {
        Throwable exception = assertThrows(PostNotFoundException.class, () -> postService.getPost(NON_EXISTING_POST_ID));

        assertEquals(POST_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void editPost() {
        PostDto dto = new PostDto();
        dto.setId(POST_1_ID);
        dto.setPostDate(POST_1_POST_DATE);
        dto.setAuthor(POST_1_AUTHOR);
        dto.setContent(NEW_POST_CONTENT);
        dto.setViewCount(POST_1_VIEW_COUNT);

        PostDto result = postService.editPost(dto);

        assertEquals(POST_1_ID, result.getId());
        assertEquals(POST_1_POST_DATE, result.getPostDate());
        assertEquals(POST_1_AUTHOR, result.getAuthor());
        assertEquals(NEW_POST_CONTENT, result.getContent());
        assertEquals(POST_1_VIEW_COUNT, result.getViewCount());
    }

    @Test
    void editNonExistingPost() {
        PostDto dto = new PostDto();
        dto.setId(NON_EXISTING_POST_ID);

        Throwable exception = assertThrows(PostNotFoundException.class, () -> postService.editPost(dto));

        assertEquals(POST_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    void deletePost() {
        postService.deletePost(POST_1_ID);

        assertFalse(postRepository.existsById(POST_1_ID));
    }

    @Test
    void deleteNonExistingPost() {
        Throwable exception = assertThrows(PostNotFoundException.class, () -> postService.deletePost(NON_EXISTING_POST_ID));

        assertEquals(POST_NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage());
    }
}
