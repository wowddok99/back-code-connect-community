package com.codeconnect.repository.post;

import com.codeconnect.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByCategoryIdAndId(Long categoryId, Long postId);
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);
}
