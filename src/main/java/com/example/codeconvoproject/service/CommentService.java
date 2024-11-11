package com.example.codeconvoproject.service;

import com.example.codeconvoproject.dto.CommentDto.CreateReplyRequest;
import com.example.codeconvoproject.dto.CommentDto.CreateReplyResponse;
import com.example.codeconvoproject.dto.CommentDto.CreateCommentResponse;
import com.example.codeconvoproject.dto.CommentDto.CreateCommentRequest;
import com.example.codeconvoproject.entity.Comment;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.entity.Reply;
import com.example.codeconvoproject.repository.CommentRepository;
import com.example.codeconvoproject.repository.PostRepository;
import com.example.codeconvoproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public CreateCommentResponse createPostComment(Long postId,
                                                   CreateCommentRequest createCommentRequest) {
        Post fetchedPost = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        Comment postComment = createCommentRequest.toEntity(fetchedPost);

        Comment postCommentPs = commentRepository.save(postComment);

        return CreateCommentResponse.builder()
                .id(postCommentPs.getId())
                .author(postCommentPs.getAuthor())
                .contents(postCommentPs.getContents())
                .createdAt(postCommentPs.getCreatedAt())
                .updatedAt(postCommentPs.getUpdatedAt())
                .build();
    }

    public CreateReplyResponse createCommentReply(Long commentId, CreateReplyRequest createReplyRequest) {
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
}
