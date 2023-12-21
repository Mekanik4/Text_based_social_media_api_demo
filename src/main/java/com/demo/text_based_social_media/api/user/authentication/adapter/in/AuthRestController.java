package com.demo.text_based_social_media.api.user.authentication.adapter.in;

import com.demo.text_based_social_media.api.user.authentication.adapter.in.dto.LoginRequestDto;
import com.demo.text_based_social_media.api.user.authentication.adapter.in.dto.LoginResponseDto;
import com.demo.text_based_social_media.api.user.authentication.adapter.in.dto.SignUpRequestDto;
import com.demo.text_based_social_media.api.user.authentication.application.port.in.LoginUseCase;
import com.demo.text_based_social_media.api.user.authentication.application.port.in.SignUpUseCase;
import com.demo.text_based_social_media.api.user.authentication.domain.LoginRequest;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthRestController {
    
    private final SignUpUseCase signUpUseCase;
    
    private final LoginUseCase loginUseCase;
    
    @PostMapping("/signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignUpRequestDto signUpRequestDto){
        signUpUseCase.signUpUser(AuthRestControllerMapper.INSTANCE.toDomain(signUpRequestDto));
        return ResponseEntity.ok("User sign up successful");
    }
    
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        ResponseCookie cookie =  loginUseCase.loginUser(AuthRestControllerMapper.INSTANCE.toDomain(loginRequestDto));
        return new LoginResponseDto(cookie.getValue());
    }
    
    @Mapper
    abstract static class AuthRestControllerMapper {
        
        public static final AuthRestControllerMapper INSTANCE = Mappers.getMapper(AuthRestControllerMapper.class);
        
        abstract SignUpRequest toDomain(SignUpRequestDto signUpRequestDto);
    
        abstract LoginRequest toDomain(LoginRequestDto loginRequestDto);
    
    }
    
}
