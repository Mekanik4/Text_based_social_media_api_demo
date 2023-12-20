package com.demo.text_based_social_media.api.user.authentication.application;

import com.demo.text_based_social_media.api.role.application.port.out.RoleReadPort;
import com.demo.text_based_social_media.api.user.authentication.application.port.in.LoginUseCase;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.WriteUserPort;
import com.demo.text_based_social_media.api.user.authentication.domain.LoginRequest;
import com.demo.text_based_social_media.config.security.jwt.JwtUtils;
import com.demo.text_based_social_media.config.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LoginService implements LoginUseCase {
    
    private final ReadUserPort readUserPort;
    
    private final WriteUserPort writeUserPort;
    
    private final RoleReadPort roleReadPort;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Override
    public ResponseCookie loginUser(final LoginRequest loginRequest) {
        try {
            String email = readUserPort.readUserByEmail(loginRequest.getEmail()).getEmail();
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return jwtUtils.generateJwtCookie(userDetails);
        } catch (AuthenticationException error) {
            throw new RuntimeException("Couldn't login user with email " + loginRequest.getEmail() + ". " + error);
        }
        
    }
    
}
