package com.example.codeconvoproject.api;

import com.example.codeconvoproject.dto.CommentDto.*;
import com.example.codeconvoproject.dto.ResponseDto;
import com.example.codeconvoproject.dto.ResponseDto.Status;
import com.example.codeconvoproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<CreateCommentResponse>> createPostComment(@PathVariable Long postId,
                                                                                @RequestBody CreateCommentRequest createCommentRequest) {
        CreateCommentResponse createCommentResponse = commentService.createPostComment(postId, createCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "게시글 댓글 등록 성공", createCommentResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<ResponseDto<UpdateCommentResponse>> updatePostComment(@PathVariable Long commentId,
                                               @RequestBody UpdateCommentRequest updateCommentRequest) {
        UpdateCommentResponse updateCommentResponse = commentService.updatePostComment(commentId, updateCommentRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "댓글 수정 성공", updateCommentResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<ResponseDto<CreateReplyResponse>> createCommentReply(@PathVariable Long commentId,
                                                                               @RequestBody CreateReplyRequest createReplyRequest) {
        CreateReplyResponse createReplyResponse = commentService.createCommentReply(commentId, createReplyRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 등록 성공", createReplyResponse),
                HttpStatus.OK
        );
    }
}
