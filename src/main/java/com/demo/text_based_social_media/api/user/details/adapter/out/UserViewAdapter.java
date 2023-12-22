package com.demo.text_based_social_media.api.user.details.adapter.out;

import com.demo.text_based_social_media.api.user.details.application.port.out.UserViewPort;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
import com.demo.text_based_social_media.mapper.UserMapper;
import com.demo.text_based_social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Repository
public class UserViewAdapter implements UserViewPort {
    
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Override
    public UserView getUserById(final Long id) {
        return userMapper
            .fromEntity(userRepository.findById(id)
                                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found with id " + id)));
    }
}
