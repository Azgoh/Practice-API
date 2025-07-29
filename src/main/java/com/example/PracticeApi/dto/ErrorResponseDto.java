package com.example.PracticeApi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Details about the error")
public class ErrorResponseDto {

    @Schema(description = "Error message", example = "User already exists")
    private String message;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    public ErrorResponseDto(String message, int status){
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
