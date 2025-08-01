package com.example.PracticeApi.controller;

import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.service.UserService;
import com.example.PracticeApi.dto.ErrorResponseDto;
import com.example.PracticeApi.dto.LoginRequestDto;
import com.example.PracticeApi.dto.RegisterRequestDto;
import com.example.PracticeApi.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful"),
            @ApiResponse(responseCode = "400", description = "User already exists",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto request){
        userService.registerUser(request);
        return ResponseEntity.ok("Registration successful. Please check your email to verify your account.");
    }

    @Operation(summary = "User email verification")
    @GetMapping(value = "/verify-email", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if("Valid".equals(result)){
            return ResponseEntity.ok("Email verified successfully.");
        }else{
            return ResponseEntity.badRequest().body("Invalid or expired verification token");
        }
    }

    @Operation(summary = "Log in a user")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request){
        String token = userService.loginUser(request);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Returns a list of all users")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers(){

        List<UserEntity> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream()
                .map(user -> userService.toDto(user))
                .toList();

        return ResponseEntity.ok(usersDto);
    }

    @Operation(summary = "Returns a user based on their id")
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUserById(@PathVariable long id){

        return userService.getUserById(id)
                .map(user -> userService.toDto(user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletes all users. Must be an admin")
    @PreAuthorize("hasRole('ADMIN')") // Only admins can delete all users
    @DeleteMapping(value = "/users/deleteAll", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted successfully");
    }

}
