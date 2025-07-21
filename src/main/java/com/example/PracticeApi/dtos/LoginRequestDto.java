package com.example.PracticeApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    private String identifier;
    private String password;
}
