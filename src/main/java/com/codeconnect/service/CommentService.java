package com.codeconnect.service;

import com.codeconnect.dto.CommentDto.FetchRepliesResponse.*;
import com.codeconnect.dto.CommentDto.FetchCommentsResponse.*;
import com.codeconnect.dto.CommentDto.*;
import com.codeconnect.entity.Comment;
import com.codeconnect.entity.Post;
import com.codeconnect.entity.Reply;
import com.codeconnect.repository.comment.CommentRepository;
import com.codeconnect.repository.post.PostRepository;
import com.codeconnect.repository.reply.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public CreateCommentResponse createComment(
            Long postId,
            CreateCommentRequest createCommentRequest
    ) {
        Post fetchedPost = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        Comment postComment = createCommentRequest.toEntity(fetchedPost);

        Comment postCommentPs = commentRepository.save(postComment);

        return CreateCommentResponse.builder()
                .id(postCommentPs.getId())
                .author(postCommentPs.getAuthor())
                .contents(postCommentPs.getContents())
                .password(postCommentPs.getPassword())
                .createdAt(postCommentPs.getCreatedAt())
                .updatedAt(postCommentPs.getUpdatedAt())
                .build();
    }

    public UpdateCommentResponse updateComment(
            Long commentId,
            UpdateCommentRequest updateCommentRequest
    ) {
        Comment fetchedComment = commentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("commentId에 해당하는 댓글이 존재하지 않습니다."));

        // 비밀번호 검증
        if (!fetchedComment.getPassword().equals(updateCommentRequest.password())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Comment comment = updateCommentRequest.toEntity(fetchedComment);
        Comment commentPs = commentRepository.save(comment);

        return UpdateCommentResponse.builder()
                .id(commentPs.getId())
                .author(commentPs.getAuthor())
                .contents(commentPs.getContents())
                .createdAt(commentPs.getCreatedAt())
                .updatedAt(commentPs.getUpdatedAt())
                .build();
    }

    public FetchCommentsResponse fetchComments(
            Long postId,
            Pageable pageable
    ) {
        Page<Comment> fetchedComments = commentRepository.findByPostId(postId, pageable);

        List<FetchedComment> comments = fetchedComments.get()
                .map(comment -> FetchedComment.builder()
                        .id(comment.getId())
                        .author(comment.getAuthor())
                        .contents(comment.getContents())
                        .createdAt(comment.getCreatedAt())
                        .updatedAt(comment.getUpdatedAt())
                        .build())
                .toList();

        return FetchCommentsResponse.builder()
                .comments(comments)
                .currentPage(fetchedComments.getNumber() + 1)
                .totalPages(fetchedComments.getTotalPages())
                .totalElements(fetchedComments.getTotalElements())
                .build();
    }

    public void deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        if (!comment.getPassword().equals(password)) {
            throw new SecurityException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.deleteById(commentId);
    }

    public CreateReplyResponse createReply(
            Long commentId,
            CreateReplyRequest createReplyRequest
    ) {
        Comment fetchedComment = commentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("commentId에 해당하는 댓글이 존재하지 않습니다."));

        Reply commentReply = createReplyRequest.toEntity(fetchedComment);

        Reply commentReplyPs = replyRepository.save(commentReply);

        return CreateReplyResponse.builder()
                .id(commentReplyPs.getId())
                .author(commentReplyPs.getAuthor())
                .contents(commentReplyPs.getContents())
                .createdAt(commentReplyPs.getCreatedAt())
                .updatedAt(commentReplyPs.getUpdatedAt())
                .build();
    }

    public UpdateReplyResponse updateReply(
            Long replyId,
            UpdateReplyRequest updateReplyRequest
    ) {
        Reply fetchedReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("replyId에 해당하는 대댓글이 존재하지 않습니다."));

        Reply reply = updateReplyRequest.toEntity(fetchedReply);

        Reply replyPs = replyRepository.save(reply);

        return UpdateReplyResponse.builder()
                .id(replyPs.getId())
                .author(replyPs.getAuthor())
                .contents(replyPs.getContents())
                .createdAt(replyPs.getCreatedAt())
                .updatedAt(replyPs.getUpdatedAt())
                .build();
    }

    public FetchRepliesResponse fetchReplies(
            Long commentId,
            Pageable pageable
    ) {
        Page<Reply> fetchedReplies = replyRepository.findByCommentId(commentId, pageable);

        List<FetchedReply> replies = fetchedReplies.get()
                .map(reply -> FetchedReply.builder()
                        .id(reply.getId())
                        .author(reply.getAuthor())
                        .contents(reply.getContents())
                        .createdAt(reply.getCreatedAt())
                        .updatedAt(reply.getUpdatedAt())
                        .build())
                .toList();

        return FetchRepliesResponse.builder()
                .replies(replies)
                .currentPage(fetchedReplies.getNumber() + 1)
                .totalPages(fetchedReplies.getTotalPages())
                .totalElements(fetchedReplies.getTotalElements())
                .build();
    }
}
