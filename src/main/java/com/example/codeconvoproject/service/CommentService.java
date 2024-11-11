package com.example.codeconvoproject.service;

import com.example.codeconvoproject.dto.CommentDto.CreateCommentResponse;
import com.example.codeconvoproject.dto.CommentDto.CreateCommentRequest;
import com.example.codeconvoproject.entity.Comment;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.repository.CommentRepository;
import com.example.codeconvoproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CreateCommentResponse createPostComment(Long postId,
                                                   CreateCommentRequest createCommentRequest) {
        Post fetchedPost = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        Comment postComment = createCommentRequest.toEntity(fetchedPost);

        Comment postCommentPs = commentRepository.save(postComment);

        return CreateCommentResponse.builder()
                .id(postCommentPs.getId())
                .contents(postCommentPs.getContents())
                .author(postCommentPs.getAuthor())
                .createdAt(postCommentPs.getCreatedAt())
                .updatedAt(postCommentPs.getUpdatedAt())
                .build();
    }
}
