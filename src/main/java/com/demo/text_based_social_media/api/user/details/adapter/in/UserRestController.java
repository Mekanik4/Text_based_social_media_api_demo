package com.demo.text_based_social_media.api.user.details.adapter.in;

import com.demo.text_based_social_media.api.user.details.adapter.in.dto.UserViewDto;
import com.demo.text_based_social_media.api.user.details.application.port.in.ViewUserUseCase;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final ViewUserUseCase viewUserUseCase;

    @GetMapping("/users/me")
    public UserViewDto getMe(@AuthenticationPrincipal UserDetailsImpl userDetails){
       return UserRestControllerMapper.INSTANCE.fromDomain(viewUserUseCase.getUserById(userDetails.getId()));
    }
    @Mapper
    abstract static class UserRestControllerMapper {
        private static final UserRestControllerMapper INSTANCE = Mappers.getMapper(UserRestControllerMapper.class);

        abstract UserViewDto fromDomain(UserView userView);
    }
}
