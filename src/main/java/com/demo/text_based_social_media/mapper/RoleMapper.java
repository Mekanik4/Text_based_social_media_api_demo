package com.demo.text_based_social_media.mapper;

import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {

    Role toDomain(RoleEntity roleEntity);

    @Mapping(target = "id", ignore = true)
    RoleEntity fromDomain(Role role);
}
