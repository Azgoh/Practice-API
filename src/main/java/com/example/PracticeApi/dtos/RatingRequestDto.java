package com.example.PracticeApi.dtos;

import lombok.Data;

@Data
public class RatingRequestDto {
    private Long professionalId;
    private int score;
    private String review;
}
