package com.demo.text_based_social_media.api.user.authentication.application;

import com.demo.text_based_social_media.api.role.application.port.out.RoleReadPort;
import com.demo.text_based_social_media.api.role.domain.Role;
import com.demo.text_based_social_media.api.user.authentication.application.port.in.SignUpUseCase;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.ReadUserPort;
import com.demo.text_based_social_media.api.user.authentication.application.port.out.WriteUserPort;
import com.demo.text_based_social_media.api.user.authentication.domain.SignUpRequest;
import com.demo.text_based_social_media.api.user.details.domain.UserCreateRequest;
import com.demo.text_based_social_media.entity.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class SignUpService implements SignUpUseCase {

    private final ReadUserPort readUserPort;

    private final WriteUserPort writeUserPort;

    private final RoleReadPort roleReadPort;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUpUser(final SignUpRequest signUpRequest) {
        if (!isEmailValid(signUpRequest.getEmail()))
            throw new RuntimeException("Invalid email address: " + signUpRequest.getEmail());
        if (readUserPort.existsByEmail(signUpRequest.getEmail()))
            throw new RuntimeException("User with email " + signUpRequest.getEmail() + " already exists");

        UserCreateRequest newUser = new UserCreateRequest();
        newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPremium(signUpRequest.isPremium());

        String role = "";

        if (signUpRequest.getRole() != null) {
            role = signUpRequest.getRole();
        }
        if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty()) {

            Role userRole = roleReadPort.getRoleByName(RoleType.USER.getName());
            role = userRole.getName();

        }
        newUser.setRole(role);
        writeUserPort.create(newUser);

    }

    public boolean isEmailValid(String emailAddress) {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(emailRegex)
                .matcher(emailAddress)
                .matches();
    }
}
