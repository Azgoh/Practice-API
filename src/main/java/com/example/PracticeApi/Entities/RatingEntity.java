package com.example.PracticeApi.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    @JsonBackReference(value = "professional-rating")
    private ProfessionalEntity professional;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-rating")
    private UserEntity reviewer;

    @Min(1)
    @Max(5)
    private int score;
    private String review;
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate(){
        timestamp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        timestamp = LocalDateTime.now();
    }
}
