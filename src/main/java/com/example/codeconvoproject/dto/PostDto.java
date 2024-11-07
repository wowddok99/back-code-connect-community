package com.example.codeconvoproject.dto;

import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.entity.PostAddress;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
) {
    @Builder
    public record CreatePostRequest(
            String title,
            String contents,
            String writer,
            String youtubeUrl,
            int likeCount,
            int dislikeCount,
            List<String> images,
            PostAddress postAddress,
            Category category
    ) {
        public Post toEntity(){
            return Post.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .writer(this.writer)
                    .youtubeUrl(this.youtubeUrl)
                    .likeCount(this.likeCount)
                    .dislikeCount(this.dislikeCount)
                    .images(this.images)
                    .postAddress((this.postAddress))
                    .category(this.category)
                    .build();
        }
    }

    @Builder
    public record CreatePostResponse(
            Long id,
            String title,
            String contents,
            String writer,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record UpdatePostRequest(
            String title,
            String contents,
            String writer,
            String youtubeUrl,
            int likeCount,
            int dislikeCount,
            List<String> images,
            PostAddress postAddress,
            Category category
    ) {}

    @Builder
    public record UpdatePostResponse(
            Long id,
            String title,
            String contents,
            String writer,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Builder
    public record FetchPostResponse(
            Long id,
            String title,
            String contents,
            String writer,
            String youtubeUrl,
            int likeCount,
            int dislikeCount,
            List<String> images,
            PostAddress postAddress,
            String categoryName
    ) {
        @Builder
        public record CategoryResponse(
                String categoryName
        ) {}
    }

    @Builder
    public record FetchPostsResponse(
        List<PostsResponse> posts,
        int currentPage,
        int totalPages,
        Long totalElements
    ) {
        @Builder
        public record PostsResponse(
                Long id,
                String title,
                String writer,
                LocalDateTime createdAt,
                LocalDateTime updatedAt

        ) {}
    }
}
