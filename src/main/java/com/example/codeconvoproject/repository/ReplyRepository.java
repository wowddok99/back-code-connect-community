package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Comment;
import com.example.codeconvoproject.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByCommentId(Long commentId, PageRequest pageRequest);

}
