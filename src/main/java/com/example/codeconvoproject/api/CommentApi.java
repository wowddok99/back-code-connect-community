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

    @PostMapping("/api/comments/{commentId}/replies")
    public ResponseEntity<ResponseDto<CreateReplyResponse>> createReply(@PathVariable Long commentId,
                                                                        @RequestBody CreateReplyRequest createReplyRequest) {
        CreateReplyResponse createReplyResponse = commentService.createReply(commentId, createReplyRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 등록 성공", createReplyResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/api/replies/{replyId}")
    public ResponseEntity<ResponseDto<UpdateReplyResponse>> updateReply(@PathVariable Long replyId,
                                         @RequestBody UpdateReplyRequest updateReplyRequest) {
        UpdateReplyResponse updateReplyResponse = commentService.updateReply(replyId, updateReplyRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "대댓글 수정 성공", updateReplyResponse),
                HttpStatus.OK
        );
    }

}
