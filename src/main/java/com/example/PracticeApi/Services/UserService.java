package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Exceptions.UserNotFoundException;
import com.example.PracticeApi.Repositories.UserRepository;
import com.example.PracticeApi.dtos.LoginRequestDto;
import com.example.PracticeApi.dtos.RegisterRequestDto;
import com.example.PracticeApi.enums.Role;
import lombok.AllArgsConstructor;
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

    public void registerUser(RegisterRequestDto request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username exists");
        }

        if(userRepository.existsByEmail((request.getEmail()))){
            throw new RuntimeException("Email already registered");
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

    public boolean loginUser(LoginRequestDto request){
        return userRepository.findByUsernameOrEmail(request.getIdentifier())
                .filter(user -> user.isEnabled() && passwordEncoder.matches(request.getPassword(), user.getPassword())).isPresent();
    }

    public Optional<UserEntity> getUserById(Long id){
        if(!userRepository.existsById(id)){
            throw  new UserNotFoundException("User not found with id: " + id);
        }
        return userRepository.findById(id);
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

}
