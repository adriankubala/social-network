package com.github.adriankubala.socialnetwork.post;

import java.util.List;

interface PostService {

    PostDto addPost(PostDto dto);

    List<PostDto> getAllPosts();

    List<PostDto> getTopTenMostViewedPosts();

    PostDto getPost(Long id);

    PostDto editPost(PostDto dto);

    void deletePost(Long id);
}
