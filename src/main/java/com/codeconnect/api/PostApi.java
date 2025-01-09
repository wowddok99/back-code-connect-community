package com.codeconnect.api;

import com.codeconnect.dto.ResponseDto;
import com.codeconnect.entity.Category;
import com.codeconnect.service.CategoryService;
import com.codeconnect.service.PostService;
import com.codeconnect.dto.PostDto.*;
import com.codeconnect.dto.PostDto.FetchPostResponse.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApi {
    private final PostService postService;
    private final CategoryService categoryService;

    @PostMapping("/api/posts/{categoryName}")
    public ResponseEntity<ResponseDto<CreatePostResponse>> createPost(@PathVariable String categoryName,
                                                                      @RequestBody CreatePostRequest createPostRequestDto) {
        // path의 categoryName을 사용하여 해당 카테고리를 가져옵니다
        Category category = categoryService.getCategory(categoryName);

        CreatePostResponse createPostResponse = postService.createPost(category, createPostRequestDto);

       return new ResponseEntity<>(
               new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 등록 성공", createPostResponse),
               HttpStatus.OK
       );
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<ResponseDto<UpdatePostResponse>> updatePost(@PathVariable Long postId,
                                                                      @RequestBody UpdatePostRequest updatePostRequest) {
        UpdatePostResponse updatePostResponse = postService.updatePost(postId, updatePostRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 수정 성공", updatePostResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/posts/{categoryName}/{postId}")
    public ResponseEntity<ResponseDto<?>> fetchPost(@PathVariable String categoryName, @PathVariable Long postId) {
        // path의 categoryName을 사용하여 해당 카테고리를 가져옵니다.
        Category category = categoryService.getCategory(categoryName);

        // categoryId, postId로 특정 게시글 단건 데이터를 조회합니다.
        FetchPostResponse fetchedPost = postService.fetchPost(category.getId(), postId);

        // postId로 특정 게시글의 상세 정보를 조회합니다.
        FetchPostResponse fetchedPostById = postService.fetchPostById(postId);

        if (fetchedPost == null && fetchedPostById != null) {
            CategoryResponse categoryResponse = CategoryResponse.builder()
                    .categoryName(fetchedPostById.categoryName())
                    .build();

            return new ResponseEntity<>(
                    new ResponseDto<>(ResponseDto.Status.FAILURE, "게시글 조회 실패: 게시글은 존재하지만, 카테고리가 일치하지 않습니다.", categoryResponse),
                    HttpStatus.NOT_FOUND
            );
        } else if (fetchedPost != null) {
            return new ResponseEntity<>(
                    new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 조회 성공", fetchedPost),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ResponseDto<>(ResponseDto.Status.FAILURE, "게시글 조회 실패: 존재하지 않는 게시글입니다.", null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/api/posts/{categoryName}")
    public ResponseEntity<ResponseDto<FetchPostsResponse>> fetchPosts(@PathVariable String categoryName,
                                                                      @RequestParam(defaultValue = "0") int pageNumber,
                                                                      @RequestParam(defaultValue = "10") int size) {
        // path의 categoryName을 사용하여 해당 카테고리를 가져옵니다.
        Category category = categoryService.getCategory(categoryName);

        // categoryId의 게시글 목록 데이터를 가져옵니다.
        FetchPostsResponse fetchPostsResponse = postService.fetchPosts(category.getId(), pageNumber, size);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "게시글 목록 조회 성공", fetchPostsResponse),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<ResponseDto<LikePostResponse>> likePost(@PathVariable Long postId) {
        LikePostResponse likePostResponse = postService.likePost(postId);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "좋아요 요청 성공", likePostResponse),
                HttpStatus.OK
        );
    }

    @PostMapping("/api/posts/{postId}/dislikes")
    public ResponseEntity<ResponseDto<DislikePostResponse>> dislikePost(@PathVariable Long postId) {
        DislikePostResponse dislikePostResponse = postService.dislikePost(postId);

        return new ResponseEntity<>(
                new ResponseDto<>(ResponseDto.Status.SUCCESS, "싫어요 요청 성공", dislikePostResponse),
                HttpStatus.OK
        );
    }
}
