package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
