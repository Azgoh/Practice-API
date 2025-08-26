package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.ReviewRequestDto;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.ReviewEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProfessionalService professionalService;

    public ReviewEntity addOrUpdateReview(ReviewRequestDto reviewRequestDto) {

        int score = reviewRequestDto.getScore();

        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        ProfessionalEntity professional = professionalService.getProfessionalById(reviewRequestDto.getProfessionalId());

        UserEntity user = userService.getAuthenticatedUser();

        Optional<ReviewEntity> existingRatingOpt = reviewRepository.findByProfessionalAndReviewer(professional, user);

        ReviewEntity review;
        if (existingRatingOpt.isPresent()) {
            review = existingRatingOpt.get();
        } else {
            review = new ReviewEntity();
            review.setProfessional(professional);
            review.setReviewer(user);
        }
        review.setScore(score);
        review.setReview(reviewRequestDto.getReview());
        review.setTimestamp(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<ReviewEntity> getReviewsForProfessionalById(Long professionalId) {
        ProfessionalEntity professional = professionalService.getProfessionalById(professionalId);
        return reviewRepository.findByProfessional(professional);
    }

    public List<ReviewEntity> getMyReceivedReviewsAsAProfessional() {
        ProfessionalEntity professional = professionalService.getAuthenticatedProfessional();
        return professional.getReviewsReceived();
    }

    public List<ReviewEntity> getMyGivenReviewsAsAUser() {
        UserEntity user = userService.getAuthenticatedUser();
        return user.getReviewsGiven();
    }

    public double getAverageRating(Long professionalId) {
        List<ReviewEntity> ratings = getReviewsForProfessionalById(professionalId);
        OptionalDouble average = ratings.stream().mapToInt(ReviewEntity::getScore).average();
        return average.orElse(0.0);
    }

}
