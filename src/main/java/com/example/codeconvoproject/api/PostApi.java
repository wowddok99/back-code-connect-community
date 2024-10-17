package com.example.codeconvoproject.api;

import com.example.codeconvoproject.dto.PostDto.CreatePostRequest;
import com.example.codeconvoproject.dto.PostDto.CreatePostResponse;
import com.example.codeconvoproject.entity.Category;
import com.example.codeconvoproject.service.CategoryService;
import com.example.codeconvoproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApi {
    private final PostService postService;
    private final CategoryService categoryService;

    @PostMapping("/api/categories/{categoryName}/posts")
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
               .category(category)
               .build()
        );

       return createPostResponse;
    }

}
