package com.codeconnect.dto;

import com.codeconnect.entity.Comment;
import com.codeconnect.entity.Post;
import com.codeconnect.entity.Reply;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record CommentDto() {
    @Builder
    public record CreateCommentRequest(
            String author,
            String contents,
            String password
    ) {
        public Comment toEntity(Post post) {
            return Comment.builder()
                    .author(this.author)
                    .contents(this.contents)
                    .password(this.password)
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
            String password,
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
    public record UpdateCommentRequest(
            String author,
            String contents,
            String password
    ) {
        public Comment toEntity(Comment comment) {
            return Comment.builder()
                    .id(comment.getId())
                    .author(this.author)
                    .contents(this.contents)
                    .password(this.password)
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
    public record FetchCommentsResponse(
            List<FetchedComment> comments,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedComment(
                Long id,
                String author,
                String contents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
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

    @Builder
    public record FetchRepliesResponse(
            List<FetchedReply> replies,
            int currentPage,
            int totalPages,
            Long totalElements
    ) {
        @Builder
        public record FetchedReply(
                Long id,
                String author,
                String contents,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
    }
}
