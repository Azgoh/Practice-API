package com.example.PracticeApi.controller;

import com.example.PracticeApi.entity.RatingEntity;
import com.example.PracticeApi.service.RatingService;
import com.example.PracticeApi.dto.RatingDto;
import com.example.PracticeApi.dto.RatingRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Rating Controller", description = "Endpoints for rating management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<RatingDto> addOrUpdateRating(
            @RequestBody RatingRequestDto request){
        RatingEntity rating = ratingService.addOrUpdateRating(
                request.getProfessionalId(),
                request.getScore(),
                request.getReview()
        );

        return ResponseEntity.ok(ratingService.toDto(rating));
    }

    @GetMapping("/professionals/{professionalId}")
    public ResponseEntity<List<RatingDto>> getRatingsForProfessional(
            @PathVariable Long professionalId
    ){
        List<RatingEntity> ratings = ratingService.getRatingsForProfessional(professionalId);
        List<RatingDto> ratingDtos = ratings.stream()
                .map(rating -> ratingService.toDto(rating))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ratingDtos);
    }

    @GetMapping("/professionals/{professionalId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long professionalId){
        double avg = ratingService.getAverageRating(professionalId);
        return ResponseEntity.ok(avg);
    }

}
