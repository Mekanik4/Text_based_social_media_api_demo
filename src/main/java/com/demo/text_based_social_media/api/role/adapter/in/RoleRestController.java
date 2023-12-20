package com.demo.text_based_social_media.api.role.adapter.in;

import com.demo.text_based_social_media.api.role.application.port.in.SaveRoleUseCase;
import com.demo.text_based_social_media.api.role.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoleRestController {
    
    private final SaveRoleUseCase saveRoleUseCase;
    
    @PostMapping("/role")
    public void addRole(@RequestBody Role role){
        saveRoleUseCase.saveRole(role);
    }
}
