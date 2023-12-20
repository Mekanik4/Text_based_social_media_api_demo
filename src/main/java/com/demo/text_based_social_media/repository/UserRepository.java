package com.demo.text_based_social_media.repository;

import com.demo.text_based_social_media.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @NonNull Optional<UserEntity> findById(@NonNull Long id);
    boolean existsByEmail(String email);
}
