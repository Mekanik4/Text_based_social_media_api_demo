package com.demo.text_based_social_media.api.user.details.adapter.out;

import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.WriteUserPort;
import com.demo.text_based_social_media.api.user.details.domain.User;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import com.demo.text_based_social_media.entity.RoleEntity;
import com.demo.text_based_social_media.entity.RoleType;
import com.demo.text_based_social_media.entity.UserEntity;
import com.demo.text_based_social_media.mapper.UserMapper;
import com.demo.text_based_social_media.repository.RoleRepository;
import com.demo.text_based_social_media.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional
public class UserAdapter implements ReadUserPort, WriteUserPort {
    
    private final UserRepository userRepository;
    
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;
    
    @Override
    public User readUserByEmail(String email) {
        return userMapper.toDomain(userRepository.findByEmail(email).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User with email " + email + " not found")
        ));
    }

    @Override
    public User readUserById(Long id) {
        return userMapper.toDomain(userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User with id " + id + " not found")
        ));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public void save(User user) {
        userRepository.save(userMapper.fromDomain(user));
    }
    
    @Override
    public void create(UserCreateRequest userCreateRequest) {
        String role = userCreateRequest.getRole();
        
        Optional<RoleEntity> roleEntity = roleRepository.findByName(role);
        UserEntity newUser = userMapper.fromDomain(userCreateRequest);
        if (roleEntity.isEmpty())
            newUser.setRole(roleRepository.getByName(RoleType.USER.getName()));
        else newUser.setRole(roleEntity.get());
        userRepository.save(newUser);
    }
}