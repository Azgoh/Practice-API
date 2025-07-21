package com.example.PracticeApi.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
}
