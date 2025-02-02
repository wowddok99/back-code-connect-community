package com.codeconnect.api;

import com.codeconnect.service.CommentService;
import com.codeconnect.dto.CommentDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment (@PathVariable Long postId,
                                                               @RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResponse createCommentResponse = commentService.createComment(postId, createCommentRequest);

        return ResponseEntity.ok(createCommentResponse);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId,
                                                               @RequestBody UpdateCommentRequest updateCommentRequest) {
        UpdateCommentResponse updateCommentResponse = commentService.updateComment(commentId, updateCommentRequest);

        return ResponseEntity.ok(updateCommentResponse);
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<FetchCommentsResponse> fetchComments (
            @PathVariable Long postId,
            Pageable pageable
    ) {
        FetchCommentsResponse fetchCommentsResponse = commentService.fetchComments(postId, pageable);

        return ResponseEntity.ok(fetchCommentsResponse);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestBody String password
    ) {
        commentService.deleteComment(commentId, password);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<CreateReplyResponse> createReply(
            @PathVariable Long commentId,
            @RequestBody CreateReplyRequest createReplyRequest
    ) {
        CreateReplyResponse createReplyResponse = commentService.createReply(commentId, createReplyRequest);

        return ResponseEntity.ok(createReplyResponse);
    }

    @PutMapping("/api/replies/{replyId}")
    public ResponseEntity<UpdateReplyResponse> updateReply(
            @PathVariable Long replyId,
            @RequestBody UpdateReplyRequest updateReplyRequest
    ) {
        UpdateReplyResponse updateReplyResponse = commentService.updateReply(replyId, updateReplyRequest);

        return ResponseEntity.ok(updateReplyResponse);
    }


    @GetMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<FetchRepliesResponse> fetchReplies (
            @PathVariable Long commentId,
            Pageable pageable
    ) {
        FetchRepliesResponse fetchRepliesResponse = commentService.fetchReplies(commentId, pageable);

        return ResponseEntity.ok(fetchRepliesResponse);
    }
}
