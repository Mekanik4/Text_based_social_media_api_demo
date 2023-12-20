package com.demo.text_based_social_media.api.user.details.adapter.out;

import com.demo.text_based_social_media.api.user.details.application.port.out.UserViewPort;
import com.demo.text_based_social_media.api.user.details.domain.UserView;
import com.demo.text_based_social_media.mapper.UserMapper;
import com.demo.text_based_social_media.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserViewAdapter implements UserViewPort {
    
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Override
    public UserView getUserById(final Long id) {
        return userMapper
            .fromEntity(userRepository.findById(id)
                                      .orElseThrow(() -> new RuntimeException("User not found with id " + id)));
    }
    
//    @Mapper
//    abstract static class UserViewAdapterMapper {
//
//        private static final UserViewAdapterMapper INSTANCE = Mappers.getMapper(UserViewAdapterMapper.class);
//
//        abstract UserView fromEntity(UserEntity userEntity);
//
//    }
    
}
