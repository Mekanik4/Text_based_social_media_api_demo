package com.demo.text_based_social_media.api.user.authentication.application.port.in;

import com.demo.text_based_social_media.api.user.authentication.domain.LoginRequest;
import org.springframework.http.ResponseCookie;

public interface LoginUseCase {
    
    ResponseCookie loginUser(LoginRequest loginRequest);

}
