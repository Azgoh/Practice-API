package com.example.PracticeApi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequestDto {

    @NotNull(message = "Professional ID cannot be null")
    private Long professionalId;

    @NotNull(message = "Score cannot be null")
    @Min(1)
    @Max(5)
    private int score;

    @Size(max = 150, message = "Review must be up to 150 characters")
    private String review;
}
