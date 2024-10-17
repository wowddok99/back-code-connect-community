package com.example.codeconvoproject.dto;

import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

public record PostDto(
) {
    @Builder
    public record CreatePostRequest(
            String title,
            String contents,
            String writer,
            Category category
    ) {
        public Post toEntity(){
            return Post.builder()
                    .title(this.title)
                    .contents(this.contents)
                    .writer(this.writer)
                    .category(this.category)
                    .build();
        }
    }

    @Builder
    public record CreatePostResponse(
            String title,
            String contents,
            String writer,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {}

}
