package com.demo.text_based_social_media.api.user.details.application.port.out;


import com.demo.text_based_social_media.api.user.details.domain.UserView;

public interface UserViewPort {
 
    UserView getUserById(Long id);
}
