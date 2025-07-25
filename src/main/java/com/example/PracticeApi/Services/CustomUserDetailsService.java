package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Exceptions.ResourceNotFoundException;
import com.example.PracticeApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException{
        UserEntity user = userRepository.findByUsernameOrEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with identifier: " + identifier));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}
