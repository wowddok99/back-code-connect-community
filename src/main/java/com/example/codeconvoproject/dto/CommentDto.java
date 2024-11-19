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

    @Builder
    public record UpdateCommentRequest(
            String author,
            String contents
    ) {
        public Comment toEntity(Comment comment) {
            return Comment.builder()
                    .id(comment.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(comment.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .post(comment.getPost()) // post: nullable = false
                    .replies(comment.getReplies()) // 댓글의 답글(replies)을 유지하여 참조가 끊어지지 않아야 함
                    .build();
        }
    }

    @Builder
    public record UpdateCommentResponse(
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record UpdateReplyRequest(
            String author,
            String contents
    ) {
        public Reply toEntity(Reply reply) {
            return Reply.builder()
                    .id(reply.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .createdAt(reply.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .comment(reply.getComment()) // comment: nullable = false
                    .build();
        }
    }

    @Builder
    public record UpdateReplyResponse(
            Long id,
            String author,
            String contents,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

}
