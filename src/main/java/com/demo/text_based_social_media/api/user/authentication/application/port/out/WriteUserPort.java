package com.demo.text_based_social_media.api.user.authentication.application.port.out;


import com.demo.text_based_social_media.api.user.details.domain.User;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;

public interface WriteUserPort {

    void save(User user);

    void create(UserCreateRequest userCreateRequest);

}
