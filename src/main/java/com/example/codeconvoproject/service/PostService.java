package com.example.codeconvoproject.service;

import com.example.codeconvoproject.dto.PostDto.*;
import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.entity.Post;
import com.example.codeconvoproject.entity.PostAddress;
import com.example.codeconvoproject.repository.PostAddressRepository;
import com.example.codeconvoproject.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostAddressRepository postAddressRepository;

    public CreatePostResponse createPost(Category category, CreatePostRequest createPostRequestDto) {
        Post post = CreatePostRequest.builder()
                .title(createPostRequestDto.title())
                .contents(createPostRequestDto.contents())
                .writer(createPostRequestDto.writer())
                .youtubeUrl(createPostRequestDto.youtubeUrl())
                .likeCount(createPostRequestDto.likeCount())
                .dislikeCount(createPostRequestDto.dislikeCount())
                .images(createPostRequestDto.images())
                .postAddress(createPostRequestDto.postAddress())
                .category(category)
                .build()
                .toEntity();

        Post postPs = postRepository.save(post);

        return CreatePostResponse.builder()
                .id(postPs.getId())
                .title(postPs.getTitle())
                .contents(postPs.getContents())
                .writer(postPs.getWriter())
                .createdAt(postPs.getCreatedAt())
                .updatedAt(postPs.getUpdatedAt())
                .build();
    }

    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post fetchedPost = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("categoryId와 postId에 해당하는 게시글이 존재하지 않습니다."));

        PostAddress postAddress = PostAddress.builder()
                .id(fetchedPost.getPostAddress().getId())
                .zipcode(updatePostRequest.postAddress().getZipcode())
                .address(updatePostRequest.postAddress().getAddress())
                .addressDetail(updatePostRequest.postAddress().getAddressDetail())
                .createdAt(updatePostRequest.postAddress().getCreatedAt())
                .build();

        postAddressRepository.save(postAddress);

        Post post = Post.builder()
                .id(fetchedPost.getId())
                .title(updatePostRequest.title())
                .contents(updatePostRequest.contents())
                .writer(updatePostRequest.writer())
                .youtubeUrl(updatePostRequest.youtubeUrl())
                .likeCount(updatePostRequest.likeCount())
                .dislikeCount(updatePostRequest.dislikeCount())
                .images(updatePostRequest.images())
                .postAddress(postAddress)
                .category(fetchedPost.getCategory())
                .createdAt(fetchedPost.getCreatedAt())
                .build();

        Post postPs = postRepository.save(post);

        return UpdatePostResponse.builder()
                .id(postPs.getId())
                .title(postPs.getTitle())
                .contents(postPs.getContents())
                .writer(postPs.getWriter())
                .createdAt(postPs.getCreatedAt())
                .updatedAt(postPs.getUpdatedAt())
                .build();
    }

    public FetchPostResponse fetchPost(Long categoryId, Long postId) {
        try {
            Post fetchedPost = postRepository.findByCategoryIdAndId(categoryId, postId)
                    .orElseThrow(() -> new RuntimeException("categoryId와 postId에 해당하는 게시글이 존재하지 않습니다."));

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
                    .orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

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

    public FetchPostsResponse fetchPosts(Long categoryId, PageRequest pageRequest){
        Page<Post> fetchedPosts = postRepository.findByCategoryId(categoryId, pageRequest);

        List<FetchPostsResponse.PostsResponse> posts = fetchedPosts.get()
                .map(post -> FetchPostsResponse.PostsResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .writer(post.getWriter())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();

        return FetchPostsResponse.builder()
                .posts(posts)
                .currentPage(fetchedPosts.getNumber())
                .totalPages(fetchedPosts.getTotalPages())
                .totalElements(fetchedPosts.getTotalElements())
                .build();
    }
}
