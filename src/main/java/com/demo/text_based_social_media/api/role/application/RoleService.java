package com.demo.text_based_social_media.api.role.application;

import com.demo.text_based_social_media.api.role.application.port.in.ReadRoleUseCase;
import com.demo.text_based_social_media.api.role.application.port.in.SaveRoleUseCase;
import com.demo.text_based_social_media.api.role.application.port.out.RoleReadPort;
import com.demo.text_based_social_media.api.role.application.port.out.RoleSavePort;
import com.demo.text_based_social_media.api.role.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService implements ReadRoleUseCase, SaveRoleUseCase {
    
    private final RoleReadPort roleReadPort;
    
    private final RoleSavePort roleSavePort;
    
    @Override
    public Role getRoleByName(final String name) {
        return roleReadPort.getRoleByName(name);
    }
    
    @Override
    public void saveRole(final Role role) {
        roleSavePort.save(role);
    }
    
}
