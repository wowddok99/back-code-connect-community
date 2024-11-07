package com.example.codeconvoproject.api;

import com.example.codeconvoproject.dto.PostDto.*;
import com.example.codeconvoproject.dto.PostDto.FetchPostResponse.CategoryResponse;
import com.example.codeconvoproject.dto.ResponseDto;
import com.example.codeconvoproject.dto.ResponseDto.Status;
import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.service.CategoryService;
import com.example.codeconvoproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

        // 게시글 생성
        CreatePostResponse createPostResponse = postService.createPost(
                CreatePostRequest.builder()
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
        );

       return new ResponseEntity<>(
               new ResponseDto<>(Status.SUCCESS, "게시글 등록 성공", createPostResponse),
               HttpStatus.OK
       );
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<ResponseDto<UpdatePostResponse>> updatePost(@PathVariable Long postId,
                                                                      @RequestBody UpdatePostRequest updatePostRequest) {
        UpdatePostResponse updatePostResponse = postService.updatePost(postId, updatePostRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "게시글 수정 성공", updatePostResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/api/post/{categoryName}/{postId}")
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
                    new ResponseDto<>(Status.FAILURE, "게시글 조회 실패: 게시글은 존재하지만, 카테고리가 일치하지 않습니다.", categoryResponse),
                    HttpStatus.NOT_FOUND
            );
        } else if (fetchedPost != null) {
            return new ResponseEntity<>(
                    new ResponseDto<>(Status.SUCCESS, "게시글 조회 성공", fetchedPost),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new ResponseDto<>(Status.FAILURE, "게시글 조회 실패: 존재하지 않는 게시글입니다.", null),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @GetMapping("/api/posts/{categoryName}/{pageNumber}")
    public ResponseEntity<ResponseDto<FetchPostsResponse>> fetchPosts(@PathVariable String categoryName,
                                                                      @PathVariable int pageNumber,
                                                                      @RequestParam(defaultValue = "10") int size) {
        // path의 categoryName을 사용하여 해당 카테고리를 가져옵니다.
        Category category = categoryService.getCategory(categoryName);
        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        // categoryId의 게시글 목록 데이터를 가져옵니다.
        FetchPostsResponse fetchPostsResponse = postService.fetchPosts(category.getId(), pageRequest);

        return new ResponseEntity<>(
                new ResponseDto<>(Status.SUCCESS, "게시글 목록 조회 성공", fetchPostsResponse),
                HttpStatus.OK
        );
    }
}
