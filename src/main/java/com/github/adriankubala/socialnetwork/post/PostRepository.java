package com.github.adriankubala.socialnetwork.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findTop10ByOrderByViewCountDesc();
}
