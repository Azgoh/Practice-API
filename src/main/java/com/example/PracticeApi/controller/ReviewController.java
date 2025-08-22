package com.example.PracticeApi.controller;

import com.example.PracticeApi.mapper.ReviewMapper;
import com.example.PracticeApi.entity.ReviewEntity;
import com.example.PracticeApi.service.ReviewService;
import com.example.PracticeApi.dto.ReviewDto;
import com.example.PracticeApi.dto.ReviewRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Rating Controller", description = "Endpoints for rating management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addOrUpdateReview(
            @RequestBody ReviewRequestDto request){
        ReviewEntity review = reviewService.addOrUpdateReview(
                request.getProfessionalId(),
                request.getScore(),
                request.getReview()
        );

        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    @GetMapping("/professionals/{professionalId}")
    public ResponseEntity<List<ReviewDto>> getReviewsForProfessional(
            @PathVariable Long professionalId
    ){
        List<ReviewEntity> reviews = reviewService.getReviewsForProfessional(professionalId);
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> reviewMapper.toDto(review))
                .collect(Collectors.toList());
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/professionals/{professionalId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long professionalId){
        double avg = reviewService.getAverageRating(professionalId);
        return ResponseEntity.ok(avg);
    }

}
