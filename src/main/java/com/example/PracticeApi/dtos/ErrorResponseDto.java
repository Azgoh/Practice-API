package com.example.PracticeApi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDto {
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponseDto(String message, int status){
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
