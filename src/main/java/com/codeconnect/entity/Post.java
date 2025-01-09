package com.codeconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="post")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String writer;

    private String youtubeUrl;

    private int likeCount;

    private int dislikeCount;

    @ElementCollection
    @CollectionTable(name = "post_image_path_list", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> imagePathList;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_address_id")
    private PostAddress postAddress;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // 더티 체킹을 위한 좋아요 수 설정 메서드
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    // 더티 체킹을 위한 싫어요 수 설정 메서드
    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
