package com.example.PracticeApi.service;

import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.enumeration.AuthProvider;
import com.example.PracticeApi.enumeration.Role;
import com.example.PracticeApi.exception.OAuth2AuthenticationProcessingException;
import com.example.PracticeApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends OidcUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        Map<String, Object> attributes = oidcUser.getAttributes();
        String email = (String) attributes.get("email");

        Optional<UserEntity> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            if (existingUser.get().getAuthProvider() != AuthProvider.GOOGLE) {
                throw new OAuth2AuthenticationProcessingException("This email is already registered via local login.");
            }
        } else {
            UserEntity newUser = new UserEntity();
            newUser.setUsername((String) attributes.get("name"));
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            newUser.setAuthProvider(AuthProvider.GOOGLE);
            newUser.setRole(Role.USER);
            newUser.setEnabled(true);
            userRepository.save(newUser);
        }

        return oidcUser;
    }
}
