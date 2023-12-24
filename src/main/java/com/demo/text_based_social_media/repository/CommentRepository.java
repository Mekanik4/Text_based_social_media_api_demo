package com.demo.text_based_social_media.repository;

import com.demo.text_based_social_media.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "SELECT DISTINCT c FROM CommentEntity c WHERE c.userId = :userId")
    List<CommentEntity> findAllByUserId(Long userId);

    @Query(value = "SELECT DISTINCT c FROM CommentEntity c WHERE c.postId = :postId ORDER BY c.postedAt DESC LIMIT 100")
    List<CommentEntity> findAllByPostId(Long postId);

    @Query(value = "SELECT COUNT(c) FROM CommentEntity c WHERE c.userId = :userId AND c.postId = :postId")
    int countByUserIdAndPostId(Long userId, Long postId);

    @Query(value = "SELECT DISTINCT c FROM CommentEntity c WHERE c.userId = :userId AND c.postId = :postId ORDER BY c.postedAt DESC LIMIT 1")
    CommentEntity findLatestCommentByUserIdAndPostId(Long userId, Long postId);

    @Query(value = "SELECT DISTINCT c FROM CommentEntity c WHERE c.postId = :postId ORDER BY c.postedAt DESC LIMIT 1")
    CommentEntity findLatestCommentByPostId(Long postId);
}
