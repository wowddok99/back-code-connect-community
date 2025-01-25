package com.codeconnect.repository.reply;

import com.codeconnect.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByCommentId(Long commentId, Pageable pageable);

}
