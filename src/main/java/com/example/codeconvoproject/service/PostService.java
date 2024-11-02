package com.example.codeconvoproject.service;

import com.example.codeconvoproject.dto.PostDto.FetchPostResponse;
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
                .id(postPs.getId())
                .title(postPs.getTitle())
                .contents(postPs.getContents())
                .writer(postPs.getWriter())
                .createAt(postPs.getCreatedAt())
                .updateAt(postPs.getUpdatedAt())
                .build();
    }

    public FetchPostResponse fetchPost(String categoryName, Long postId) {
        try {
            Post fetchedPost = postRepository.findByCategoryNameAndId(categoryName,postId)
                    .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

            return FetchPostResponse.builder()
                    .id(fetchedPost.getId())
                    .title(fetchedPost.getTitle())
                    .contents(fetchedPost.getContents())
                    .writer(fetchedPost.getWriter())
                    .youtubeUrl(fetchedPost.getYoutubeUrl())
                    .likeCount(fetchedPost.getLikeCount())
                    .dislikeCount(fetchedPost.getDislikeCount())
                    .images(fetchedPost.getImages())
                    .postAddress(fetchedPost.getPostAddress())
                    .categoryName(fetchedPost.getCategory().getName())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    public FetchPostResponse fetchPostById(Long postId) {
        try {
            Post fetchedPostById = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

            return FetchPostResponse.builder()
                    .id(fetchedPostById.getId())
                    .title(fetchedPostById.getTitle())
                    .contents(fetchedPostById.getContents())
                    .writer(fetchedPostById.getWriter())
                    .youtubeUrl(fetchedPostById.getYoutubeUrl())
                    .likeCount(fetchedPostById.getLikeCount())
                    .dislikeCount(fetchedPostById.getDislikeCount())
                    .images(fetchedPostById.getImages())
                    .postAddress(fetchedPostById.getPostAddress())
                    .categoryName(fetchedPostById.getCategory().getName())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}
