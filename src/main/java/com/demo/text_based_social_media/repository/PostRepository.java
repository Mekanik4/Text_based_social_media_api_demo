package com.demo.text_based_social_media.repository;

import com.demo.text_based_social_media.entity.PostEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = "SELECT p FROM PostEntity p WHERE p.id = :id")
    Optional<PostEntity> findById(@NonNull Long id);

//    @Query(value = "SELECT DISTINCT p FROM PostEntity p JOIN FETCH p.creator c WHERE p.creatorId = :userId")
//    List<PostEntity> findAllByUserId(Long userId);
}
