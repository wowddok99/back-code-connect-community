package com.codeconnect.service;

import com.codeconnect.entity.Category;
import com.codeconnect.dto.PostDto.FetchPostsResponse.FetchedPostDto;
import com.codeconnect.dto.PostDto.*;
import com.codeconnect.entity.Post;
import com.codeconnect.entity.PostAddress;
import com.codeconnect.repository.PostAddressRepository;
import com.codeconnect.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .password(createPostRequestDto.password())
                .youtubeUrl(createPostRequestDto.youtubeUrl())
                .imagePathList(createPostRequestDto.imagePathList())
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

    @Transactional
    public UpdatePostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        // 기존 게시물 조회, 게시물이 없으면 예외 발생
        Post fetchedPost = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        // 기존 게시물의 주소 정보를 수정하기 위해 PostAddress 객체 생성
        PostAddress postAddress = PostAddress.builder()
                .id(fetchedPost.getPostAddress().getId())
                .zipcode(updatePostRequest.postAddress().getZipcode())
                .address(updatePostRequest.postAddress().getAddress())
                .addressDetail(updatePostRequest.postAddress().getAddressDetail())
                .createdAt(fetchedPost.getPostAddress().getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        postAddressRepository.save(postAddress);

        // 기존 게시물의 정보를 업데이트하기 위해 Post 객체 생성
        Post post = Post.builder()
                .id(fetchedPost.getId())
                .title(updatePostRequest.title())
                .contents(updatePostRequest.contents())
                .writer(updatePostRequest.writer())
                .youtubeUrl(updatePostRequest.youtubeUrl())
                .likeCount(fetchedPost.getLikeCount())
                .dislikeCount(fetchedPost.getDislikeCount())
                .imagePathList(updatePostRequest.imagePathList())
                .postAddress(postAddress)
                .category(fetchedPost.getCategory())
                .createdAt(fetchedPost.getCreatedAt())
                .updatedAt(LocalDateTime.now())
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
                    .imagePathList(fetchedPost.getImagePathList())
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
                    .imagePathList(fetchedPostById.getImagePathList())
                    .postAddress(fetchedPostById.getPostAddress())
                    .categoryName(fetchedPostById.getCategory().getName())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    public FetchPostsResponse fetchPosts(Long categoryId, int pageNumber, int size){
        // Sort 객체를 생성하여 정렬 기준을 설정합니다.
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 페이지 번호와 페이지 크기를 사용하여 PageRequest 객체를 생성합니다.
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size, sort);

        Page<Post> fetchedPosts = postRepository.findByCategoryId(categoryId, pageRequest);

        List<FetchedPostDto> posts = fetchedPosts.get()
                .map(post -> FetchedPostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .writer(post.getWriter())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();

        return FetchPostsResponse.builder()
                .posts(posts)
                .currentPage(fetchedPosts.getNumber() + 1)
                .totalPages(fetchedPosts.getTotalPages())
                .totalElements(fetchedPosts.getTotalElements())
                .build();
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다.");
        }

        // 게시글 삭제
        postRepository.deleteById(postId);
    }
    @Transactional
    public LikePostResponse likePost(Long postId) {
        Post fetchedPostById = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        // 더티 체킹에 의해 좋아요 수를 1 증가시킴
        fetchedPostById.setLikeCount(fetchedPostById.getLikeCount() + 1);

        return LikePostResponse.builder()
                .likeCount(fetchedPostById.getLikeCount())
                .build();
    }

    @Transactional
    public DislikePostResponse dislikePost(Long postId) {
        Post fetchedPostById = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("postId에 해당하는 게시글이 존재하지 않습니다."));

        // 더티 체킹에 의해 싫어요 수를 1 증가시킴
        fetchedPostById.setDislikeCount(fetchedPostById.getDislikeCount() + 1);

        return DislikePostResponse.builder()
                .dislikeCount(fetchedPostById.getDislikeCount())
                .build();
    }

}
