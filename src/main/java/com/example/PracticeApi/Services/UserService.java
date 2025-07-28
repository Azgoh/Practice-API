package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Exceptions.AlreadyExistsException;
import com.example.PracticeApi.Exceptions.ResourceNotFoundException;
import com.example.PracticeApi.Repositories.UserRepository;
import com.example.PracticeApi.Security.JwtUtil;
import com.example.PracticeApi.dtos.LoginRequestDto;
import com.example.PracticeApi.dtos.RegisterRequestDto;
import com.example.PracticeApi.dtos.UserDto;
import com.example.PracticeApi.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

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
        user.setRole(Role.ADMIN); //Change this

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
        UserEntity optUser = findUserByUsernameOrEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!optUser.isEnabled()){
            throw new DisabledException("Account is not enabled. Please verify your email");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(authentication.getName());
    }

    public UserEntity getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String identifier = auth.getName();
        return userRepository.findByUsernameOrEmail(identifier)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Optional<UserEntity> getUserById(Long id){
        Optional<UserEntity> optUser = userRepository.findById(id);
        if(optUser.isEmpty()){
            throw new ResourceNotFoundException("User not found");
        }
        return optUser;
    }
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserByUsernameOrEmail(String identifier){
        return userRepository.findByUsernameOrEmail(identifier);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public UserDto toDto(UserEntity user){
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled());
    }

}
