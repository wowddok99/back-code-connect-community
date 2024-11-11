package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
