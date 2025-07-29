package com.example.PracticeApi.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingRequestDto {

    @NotBlank(message = "Professional ID is required")
    private Long professionalId;

    @NotBlank(message = "Score is required")
    @Min(1)
    @Max(5)
    private int score;

    @Size(max = 150, message = "Review must be up to 150 characters")
    private String review;
}
