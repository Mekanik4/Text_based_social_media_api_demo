package com.demo.text_based_social_media.api.role.application.port.out;


import com.demo.text_based_social_media.api.role.domain.Role;

public interface RoleReadPort {

    Role getRoleByName(String name);

    boolean existsByName(String name);

}
