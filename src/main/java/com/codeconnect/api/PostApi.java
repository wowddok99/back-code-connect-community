package com.codeconnect.api;

import com.codeconnect.entity.Category;
import com.codeconnect.service.CategoryService;
import com.codeconnect.service.PostService;
import com.codeconnect.dto.PostDto.*;
import com.codeconnect.dto.PostDto.FetchPostResponse.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class PostApi {
    private final PostService postService;
    private final CategoryService categoryService;

    @PostMapping("/api/posts/{categoryName}")
    public ResponseEntity<CreatePostResponse> createPost (
            @PathVariable String categoryName,
            @RequestBody CreatePostRequest createPostRequestDto
    ) {
        Category category = categoryService.getCategory(categoryName);

        CreatePostResponse createPostResponse = postService.createPost(category, createPostRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createPostResponse);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost (
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest updatePostRequest
    ) {
        UpdatePostResponse updatePostResponse = postService.updatePost(postId, updatePostRequest);

        return ResponseEntity.ok(updatePostResponse);
    }

    @GetMapping("/api/posts/{categoryName}/{postId}")
    public ResponseEntity<?> fetchPost (
            @PathVariable String categoryName,
            @PathVariable Long postId
    ) {
        Category category = categoryService.getCategory(categoryName);

        FetchPostResponse fetchedPost = postService.fetchPost(category.getId(), postId);

        return ResponseEntity.ok(fetchedPost);
    }


    @GetMapping("/api/posts/{categoryName}")
    public ResponseEntity<FetchPostsResponse> fetchPosts (
            @PathVariable String categoryName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Pageable pageable
    ) {
        Category category = categoryService.getCategory(categoryName);

        FetchPostsResponse fetchPostsResponse = postService.fetchPosts(category.getId(), title, startDate, endDate, pageable);

        return ResponseEntity.ok(fetchPostsResponse);
    }


    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<Void> deletePost (@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<LikePostResponse> likePost (@PathVariable Long postId) {
        LikePostResponse likePostResponse = postService.likePost(postId);

        return ResponseEntity.ok(likePostResponse);
    }

    @PostMapping("/api/posts/{postId}/dislikes")
    public ResponseEntity<DislikePostResponse> dislikePost (@PathVariable Long postId) {
        DislikePostResponse dislikePostResponse = postService.dislikePost(postId);

        return ResponseEntity.ok(dislikePostResponse);
    }
}
