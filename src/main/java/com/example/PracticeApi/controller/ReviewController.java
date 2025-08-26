package com.example.PracticeApi.controller;

import com.example.PracticeApi.mapper.ReviewMapper;
import com.example.PracticeApi.entity.ReviewEntity;
import com.example.PracticeApi.service.ReviewService;
import com.example.PracticeApi.dto.ReviewDto;
import com.example.PracticeApi.dto.ReviewRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addOrUpdateReview(
            @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewEntity review = reviewService.addOrUpdateReview(reviewRequestDto);

        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    @GetMapping("/professionals/{professionalId}")
    public ResponseEntity<List<ReviewDto>> getReviewsForProfessionalById(
            @PathVariable Long professionalId
    ){
        List<ReviewEntity> reviews = reviewService.getReviewsForProfessionalById(professionalId);
        return ResponseEntity.ok(reviewMapper.toDtoList(reviews));
    }

    @GetMapping("/professionals/{professionalId}/average")
    public ResponseEntity<Double> getAverageRatingByProfessionalId(@PathVariable Long professionalId){
        double avg = reviewService.getAverageRating(professionalId);
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/my-received-reviews")
    public ResponseEntity<List<ReviewDto>> getMyReceivedReviewsAsAProfessional(){
        List<ReviewEntity> reviews = reviewService.getMyReceivedReviewsAsAProfessional();
        return ResponseEntity.ok(reviewMapper.toDtoList(reviews));
    }

    @GetMapping("/my-given-reviews")
    public ResponseEntity<List<ReviewDto>> getMyGivenReviewsAsAUser(){
        List<ReviewEntity> reviews = reviewService.getMyGivenReviewsAsAUser();
        return ResponseEntity.ok(reviewMapper.toDtoList(reviews));
    }

}
