package com.demo.text_based_social_media.api.role.adapter.out;

import com.demo.text_based_social_media.api.role.application.port.out.RoleReadPort;
import com.demo.text_based_social_media.api.role.application.port.out.RoleSavePort;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.mapper.RoleMapper;
import com.demo.text_based_social_media.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RoleAdapter implements RoleReadPort, RoleSavePort {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Role getRoleByName(final String name) {
        return roleMapper.toDomain(roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Can't find role with name " + name)));
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }


    @Override
    public void save(final Role role) {
        roleRepository.save(roleMapper.fromDomain(role));
    }

//    @Mapper
//    abstract static class RoleAdapterMapper {
//        public static final RoleAdapterMapper INSTANCE = Mappers.getMapper(RoleAdapterMapper.class);
//
//        abstract Role toDomain(RoleEntity roleEntity);
//
//        @Mapping(target = "id", ignore = true)
//        abstract RoleEntity fromDomain(Role role);
//    }

}
