package com.example.PracticeApi.Controllers;

import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Services.UserService;
import com.example.PracticeApi.dtos.LoginRequestDto;
import com.example.PracticeApi.dtos.RegisterRequestDto;
import com.example.PracticeApi.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request){
        try{
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request){
        boolean success = userService.loginUser(request);
        if(success){
            return ResponseEntity.ok("User logged in successfully");
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user credentials");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        List<UserEntity> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream()
                .map(user -> new UserDto(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()))
                .toList();

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id){

        return userService.getUserById(id)
                .map(user -> new UserDto(user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
