package com.demo.text_based_social_media.api.user.details.application.port.in;

import com.demo.text_based_social_media.api.user.details.domain.UserView;

public interface ViewUserUseCase {
    UserView getUserById(Long id);
}
