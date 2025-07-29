package com.example.PracticeApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    private Long reviewId;
    private Long professionalId;
    private Long reviewerId;
    private int score;
    private String review;
    private LocalDateTime timestamp;
}
