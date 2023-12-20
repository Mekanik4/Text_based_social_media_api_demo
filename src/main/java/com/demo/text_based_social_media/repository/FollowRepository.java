package com.demo.text_based_social_media.repository;

import com.demo.text_based_social_media.entity.FollowEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    FollowEntity getById(@NonNull Long id);

    @Query(value = "SELECT DISTINCT f FROM FollowEntity f WHERE f.followerId = :followerId")
    List<FollowEntity> getAllByFollowerId(Long followerId);

    Optional<FollowEntity> getByFollowerIdAndFollowingId(Long followerId, Long followingId);
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
