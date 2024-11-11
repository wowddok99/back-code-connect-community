package com.example.codeconvoproject.dto;

import com.example.codeconvoproject.entity.Comment;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.entity.Reply;
import lombok.Builder;

import java.time.LocalDateTime;

public record CommentDto() {
    @Builder
    public record CreateCommentRequest(
            String author,
            String contents
    ) {
        public Comment toEntity(Post post) {
            return Comment.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .post(post)
                    .build();
        }
    }

    @Builder
    public record CreateCommentResponse(
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record CreateReplyRequest(
            String author,
            String contents
    ) {
        public Reply toEntity(Comment comment) {
            return Reply.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .comment(comment)
                    .build();
        }
    }

    @Builder
    public record CreateReplyResponse(
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
