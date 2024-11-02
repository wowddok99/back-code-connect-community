package com.example.codeconvoproject.api;

import com.example.codeconvoproject.dto.PostDto.FetchPostResponse;
import com.example.codeconvoproject.dto.PostDto.CreatePostRequest;
import com.example.codeconvoproject.dto.PostDto.CreatePostResponse;
import com.example.codeconvoproject.dto.ResponseDto;
import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.service.CategoryService;
import com.example.codeconvoproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApi {
    private final PostService postService;
    private final CategoryService categoryService;

    @PostMapping("/api/posts/{categoryName}")
    public CreatePostResponse createPost(@PathVariable String categoryName,
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

       return createPostResponse;
    }
    @GetMapping("/api/posts/{categoryName}/{postId}")
    public ResponseEntity<ResponseDto<FetchPostResponse>> fetchPost(@PathVariable String categoryName, @PathVariable Long postId) {
        // categoryName, postId로 특정 게시글의 상세 정보를 조회합니다.
        FetchPostResponse fetchedPost = postService.fetchPost(categoryName,postId);

        // postId로 특정 게시글의 상세 정보를 조회합니다.
        FetchPostResponse fetchedPostById = postService.fetchPostById(postId);

        if (fetchedPost == null && fetchedPostById != null) {
            return new ResponseEntity<>(
                    new ResponseDto<>(ResponseDto.Status.FAILURE, "게시글 조회 실패: 게시글은 존재하지만, 카테고리가 일치하지 않습니다.", fetchedPostById),
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
}
