package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Post> findByCategoryNameAndId(String categoryName, Long id);
}
