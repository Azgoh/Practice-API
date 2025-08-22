package com.example.PracticeApi.mapper;

import com.example.PracticeApi.dto.ReviewDto;
import com.example.PracticeApi.entity.ReviewEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewMapper {

    public ReviewDto toDto(ReviewEntity review){
        return new ReviewDto(review.getId(),
                review.getReviewer().getId(),
                review.getProfessional().getId(),
                review.getProfessional().getFirstName(),
                review.getProfessional().getLastName(),
                review.getReviewer().getUsername(),
                review.getScore(),
                review.getReview(),
                review.getTimestamp());
    }

    public List<ReviewDto> toDtoList(List<ReviewEntity> reviews) {
        return reviews.stream()
                .map(this::toDto)
                .toList();
    }
}
