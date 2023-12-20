package com.demo.text_based_social_media.api.role.application.port.in;


import com.demo.text_based_social_media.api.role.domain.Role;

public interface ReadRoleUseCase {
    
    Role getRoleByName(String name);
    
}
