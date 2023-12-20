package com.demo.text_based_social_media.api.user.details.application;

import com.demo.text_based_social_media.api.user.details.application.port.in.ViewUserUseCase;
import com.demo.text_based_social_media.api.user.details.application.port.out.UserViewPort;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ViewUserUseCase {
    
    private final UserViewPort userViewPort;
    @Override
    public UserView getUserById(final Long id) {
        return userViewPort.getUserById(id);
    }
    
}
