package com.example.PracticeApi.service;

import com.example.PracticeApi.mapper.ProfessionalMapper;
import com.example.PracticeApi.mapper.ReviewMapper;
import com.example.PracticeApi.dto.*;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.enumeration.AuthProvider;
import com.example.PracticeApi.exception.AlreadyExistsException;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.mapper.UserMapper;
import com.example.PracticeApi.repository.ProfessionalRepository;
import com.example.PracticeApi.repository.UserRepository;
import com.example.PracticeApi.enumeration.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.PracticeApi.component.JwtUtils;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ProfessionalMapper professionalMapper;
    private final UserMapper userMapper;

    public void registerUser(RegisterRequestDto request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AlreadyExistsException("Username exists");
        }

        if(userRepository.existsByEmail((request.getEmail()))){
            throw new AlreadyExistsException("Email already registered");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        user.setRole(Role.USER); //Change this
        user.setAuthProvider(AuthProvider.LOCAL);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);

        String confirmationUrl = "http://localhost:8080/api/verify-email?token=" + token;
        emailService.sendEmail(user.getEmail(), "Email Verification", "Click the " +
                "link to verify your email: " + confirmationUrl);
    }

    public String validateVerificationToken(String token){
        UserEntity user = userRepository.findByVerificationToken(token).orElse(null);
        if(user == null){
            return "Invalid";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "Valid";
    }

    public String loginUser(LoginRequestDto request){
        String identifier = request.getIdentifier();
        UserEntity optUser = userRepository.findByUsernameOrEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!optUser.isEnabled()){
            throw new DisabledException("Account is not enabled. Please verify your email");
        }

        if(optUser.getAuthProvider() != AuthProvider.LOCAL){
            throw new IllegalStateException("This account is already registered via Google login");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(authentication.getName());
    }

    public UserEntity getAuthenticatedUser(){
        String identifier = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameOrEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserWithProfessionalDto getCurrentUserWithProfessional(){
        UserEntity user = getAuthenticatedUser();
        ProfessionalEntity professional = professionalRepository.findByUser(user).orElse(null);

        UserDto userDto = userMapper.toDto(user);

        ProfessionalDto profDto = professional != null
                ? professionalMapper.toDto(professional)
                : null;

        return new UserWithProfessionalDto(
                userDto,
                profDto
        );
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
