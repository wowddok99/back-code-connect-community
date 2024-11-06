package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByCategoryIdAndId(Long categoryId, Long postId);
    Page<Post> findByCategoryId(Long categoryId, PageRequest pageRequest);
}
