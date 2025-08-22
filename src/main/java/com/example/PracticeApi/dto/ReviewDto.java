package com.example.PracticeApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long reviewId;
    private Long reviewerId;
    private Long professionalId;
    private String professionalFirstName;
    private String professionalLastName;
    private String reviewerUsername;
    private int score;
    private String review;
    private LocalDateTime timestamp;
}
