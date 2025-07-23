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

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        user.setRole(Role.ADMIN); //Change this

        userRepository.save(user);
    }

    public boolean loginUser(LoginRequestDto request){
        return userRepository.findByUsernameOrEmail(request.getIdentifier())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword())).isPresent();
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
