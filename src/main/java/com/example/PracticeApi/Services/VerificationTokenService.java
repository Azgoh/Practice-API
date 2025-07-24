package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    @Autowired
    private UserRepository userRepository;

    public void createVerificationToken(UserEntity user, String token){
        user.setVerificationToken(token);
        userRepository.save(user);
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
}
