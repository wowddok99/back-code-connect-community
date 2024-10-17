package com.example.codeconvoproject.service;

import com.example.codeconvoproject.dto.PostDto.CreatePostResponse;
import com.example.codeconvoproject.dto.PostDto.CreatePostRequest;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public CreatePostResponse createPost(CreatePostRequest createPostRequestDto) {

        Post post = createPostRequestDto.toEntity();
        Post postPs = postRepository.save(post);

        return CreatePostResponse.builder()
                .title(post.getTitle())
                .contents(post.getContents())
                .writer(post.getWriter())
                .createAt(post.getCreatedAt())
                .updateAt(post.getUpdatedAt())
                .build();
    }
}
