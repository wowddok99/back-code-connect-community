package com.codeconnect.api;

import com.codeconnect.service.CommentService;
import com.codeconnect.dto.CommentDto.*;
import com.codeconnect.dto.ResponseDto;
import com.codeconnect.dto.ResponseDto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CreateCommentResponse>> createComment(@PathVariable Long postId,
                                                                            @RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResponse createCommentResponse = commentService.createComment(postId, createCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "게시글 댓글 등록 성공", createCommentResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<ResponseDto<UpdateCommentResponse>> updateComment(@PathVariable Long commentId,
                                                                            @RequestBody UpdateCommentRequest updateCommentRequest) {
        UpdateCommentResponse updateCommentResponse = commentService.updateComment(commentId, updateCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "댓글 수정 성공", updateCommentResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<FetchCommentsResponse>> fetchComments (
            @PathVariable Long postId,
            Pageable pageable
    ) {
        FetchCommentsResponse fetchCommentsResponse = commentService.fetchComments(postId, pageable);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "댓글 조회 성공", fetchCommentsResponse),
                HttpStatus.OK
        );
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
    public ResponseEntity<ResponseDto<CreateReplyResponse>> createReply(
            @PathVariable Long commentId,
            @RequestBody CreateReplyRequest createReplyRequest
    ) {
        CreateReplyResponse createReplyResponse = commentService.createReply(commentId, createReplyRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 등록 성공", createReplyResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/api/replies/{replyId}")
    public ResponseEntity<ResponseDto<UpdateReplyResponse>> updateReply(
            @PathVariable Long replyId,
            @RequestBody UpdateReplyRequest updateReplyRequest
    ) {
        UpdateReplyResponse updateReplyResponse = commentService.updateReply(replyId, updateReplyRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 수정 성공", updateReplyResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<ResponseDto<FetchRepliesResponse>> fetchReplies (
            @PathVariable Long commentId,
            Pageable pageable
    ) {
       FetchRepliesResponse fetchRepliesResponse = commentService.fetchReplies(commentId, pageable);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 조회 성공", fetchRepliesResponse),
                HttpStatus.OK
        );
    }
}
