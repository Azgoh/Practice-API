package com.example.PracticeApi.Controllers;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Services.UserService;
import com.example.PracticeApi.dtos.LoginRequestDto;
import com.example.PracticeApi.dtos.RegisterRequestDto;
import com.example.PracticeApi.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request){
        userService.registerUser(request);
        return ResponseEntity.ok("Registration successful. Please check your email to verify your account.");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if("Valid".equals(result)){
            return ResponseEntity.ok("Email verified successfully.");
        }else{
            return ResponseEntity.badRequest().body("Invalid or expired verification token");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request){
        String token = userService.loginUser(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        List<UserEntity> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream()
                .map(user -> userService.toDto(user))
                .toList();

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id){

        return userService.getUserById(id)
                .map(user -> userService.toDto(user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')") // Only admins can delete all users
    @DeleteMapping("/users/deleteAll")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted successfully");
    }

}
