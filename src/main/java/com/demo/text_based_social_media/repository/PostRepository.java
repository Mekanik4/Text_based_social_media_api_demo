package com.demo.text_based_social_media.repository;

import com.demo.text_based_social_media.entity.PostEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = "SELECT p FROM PostEntity p WHERE p.id = :id")
    Optional<PostEntity> findById(@NonNull Long id);

//    @Query(value = "SELECT DISTINCT p FROM PostEntity p WHERE p.userId = :userId")
    List<PostEntity> findAllByUserIdOrderByPostedAtDesc(Long userId);

    List<PostEntity> findAllByUserIdInOrderByPostedAtDesc(List<Long> userIds);

    @Query(value = "SELECT DISTINCT p.id FROM PostEntity p WHERE p.userId = :userId")
    List<Long> findPostIdsByUserId(Long userId);

    @Query(value = "SELECT DISTINCT p.id FROM PostEntity p WHERE p.userId IN :followingIds")
    List<Long> findPostIdsByFollowingIds(List<Long> followingIds);
}
