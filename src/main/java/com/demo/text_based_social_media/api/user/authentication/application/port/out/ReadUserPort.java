package com.demo.text_based_social_media.api.user.authentication.application.port.out;


import com.demo.text_based_social_media.api.user.details.domain.User;

public interface ReadUserPort {

    User readUserByEmail(String email);

    User readUserById(Long id);

    boolean existsByEmail(String email);
}
