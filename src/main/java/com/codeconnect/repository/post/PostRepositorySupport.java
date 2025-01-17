package com.codeconnect.repository.post;

import com.codeconnect.entity.Post;
import com.codeconnect.entity.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {
    QPost post = QPost.post;

    public PostRepositorySupport() {
        super(Post.class);
    }

    public Page<Post> findByCategoryId(
            Long categoryId,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        // 필터링 조건 생성
        BooleanExpression filterConditions = createFilterConditions(categoryId, title, startDate, endDate);

        // 데이터 쿼리
        var query = getQuerydsl().createQuery()
                .select(post)
                .from(post)
                .where(filterConditions)
                .offset(pageable.getOffset()) // 현재 페이지의 오프셋 설정
                .limit(pageable.getPageSize()); // 페이지 크기 설정

        // 결과 조회
        var result = query.fetch();

        // 총 개수 쿼리
        var totalCount = getQuerydsl().createQuery()
                .select(post.count())
                .from(post)
                .where(filterConditions)
                .fetchOne();

        // Page 객체 반환
        return new PageImpl<>(result, pageable, totalCount);
    }

    /**
     * 필터링 조건을 생성하는 메서드
     */
    private BooleanExpression createFilterConditions(
            Long categoryId,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        BooleanExpression condition = post.category.id.eq(categoryId);

        if (title != null && !title.trim().isEmpty()) {
            condition = condition.and(post.title.eq(title));
        }

        if (startDate != null) {
            condition = condition.and(post.createdAt.goe(startDate));
        }

        if (endDate != null) {
            condition = condition.and(post.createdAt.loe(endDate));
        }

        return condition;
    }
}
