package com.example.codeconvoproject.dto;

import com.example.codeconvoproject.entity.Comment;
import com.example.codeconvoproject.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

public record CommentDto() {
    @Builder
    public record CreateCommentRequest(
            String contents,
            String author
    ) {
        public Comment toEntity(Post post){
            return Comment.builder()
                    .contents(this.contents)
                    .author(this.author)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .post(post)
                    .build();
        }
    }

    @Builder
    public record CreateCommentResponse(
            Long id,
            String contents,
            String author,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
