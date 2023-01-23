package com.github.adriankubala.socialnetwork.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    ResponseEntity<Object> addPost(@RequestBody PostDto dto) {
        PostDto result = postService.addPost(dto);
        return ResponseEntity.created(buildUriExpandedById(result)).build();
    }

    private static URI buildUriExpandedById(PostDto dto) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
    }

    @GetMapping
    ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/top-ten")
    ResponseEntity<List<PostDto>> getTopTenMostViewedPosts() {
        return ResponseEntity.ok(postService.getTopTenMostViewedPosts());
    }

    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping
    ResponseEntity<PostDto> editPost(@RequestBody PostDto dto) {
        return ResponseEntity.ok(postService.editPost(dto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
