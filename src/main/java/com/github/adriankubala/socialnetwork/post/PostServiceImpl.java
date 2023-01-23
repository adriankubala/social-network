package com.github.adriankubala.socialnetwork.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public PostDto addPost(PostDto dto) {
        Post entity = mapDtoToEntity(dto);
        postRepository.save(entity);
        return mapEntityToDto(entity);
    }

    private static Post mapDtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setPostDate(dto.getPostDate());
        entity.setAuthor(dto.getAuthor());
        entity.setContent(dto.getContent());
        entity.setViewCount(dto.getViewCount());
        return entity;
    }

    private static PostDto mapEntityToDto(Post entity) {
        PostDto dto = new PostDto();
        dto.setId(entity.getId());
        dto.setPostDate(entity.getPostDate());
        dto.setAuthor(entity.getAuthor());
        dto.setContent(entity.getContent());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostServiceImpl::mapEntityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDto> getTopTenMostViewedPosts() {
        return postRepository.findTop10ByOrderByViewCountDesc().stream()
                .map(PostServiceImpl::mapEntityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto getPost(Long id) {
        return postRepository.findById(id)
                .map(PostServiceImpl::mapEntityToDto)
                .orElseThrow(PostServiceImpl::getPostNotFoundException);
    }

    private static PostNotFoundException getPostNotFoundException() {
        return new PostNotFoundException("Post with given id not found.");
    }

    @Transactional
    @Override
    public PostDto editPost(PostDto dto) {
        return postRepository.findById(dto.getId())
                .map(entity -> mapDtoToEntity(dto, entity))
                .map(PostServiceImpl::mapEntityToDto)
                .orElseThrow(PostServiceImpl::getPostNotFoundException);
    }

    private static Post mapDtoToEntity(PostDto dto, Post entity) {
        entity.setPostDate(dto.getPostDate());
        entity.setAuthor(dto.getAuthor());
        entity.setContent(dto.getContent());
        entity.setViewCount(dto.getViewCount());
        return entity;
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.findById(id).ifPresentOrElse(postRepository::delete, () -> {
            throw getPostNotFoundException();
        });
    }
}
