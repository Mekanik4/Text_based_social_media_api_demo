package com.demo.text_based_social_media.api.role.application.port.out;


import com.demo.text_based_social_media.api.role.domain.Role;

public interface RoleSavePort {
    
    void save(Role role);
    
}
