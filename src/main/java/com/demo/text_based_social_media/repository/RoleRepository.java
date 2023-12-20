package com.demo.text_based_social_media.repository;


import com.demo.text_based_social_media.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

    RoleEntity getByName(String name);
    boolean existsByName(String name);

}
