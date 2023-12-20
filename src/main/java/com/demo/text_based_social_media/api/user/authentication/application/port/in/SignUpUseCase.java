package com.demo.text_based_social_media.api.user.authentication.application.port.in;


import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;

public interface SignUpUseCase {
    void signUpUser(SignUpRequest userSignUpRequest);
    
}
