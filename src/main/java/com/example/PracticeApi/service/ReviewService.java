package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.ProfessionalDto;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.ReviewEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.repository.ProfessionalRepository;
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
    private final ProfessionalRepository professionalRepository;
    private final UserService userService;

    public ReviewEntity addOrUpdateReview(Long professionalId, int score, String review){

        if(score < 1 || score > 5){
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));

        UserEntity user = userService.getAuthenticatedUser();

        Optional<ReviewEntity> existingRatingOpt = reviewRepository.findByProfessionalAndReviewer(professional, user);

        ReviewEntity rating;
        if(existingRatingOpt.isPresent()){
            rating = existingRatingOpt.get();
        } else {
            rating = new ReviewEntity();
            rating.setProfessional(professional);
            rating.setReviewer(user);
        }
        rating.setScore(score);
        rating.setReview(review);
        rating.setTimestamp(LocalDateTime.now());
        return reviewRepository.save(rating);
    }

    public List<ReviewEntity> getReviewsForProfessional(Long professionalId){
        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));
        return reviewRepository.findByProfessional(professional);
    }

    public List<ReviewEntity> getMyReceivedReviewsAsAProfessional(){
        UserEntity user = userService.getAuthenticatedUser();
        ProfessionalEntity professional = professionalRepository.findByUser(user).orElse(null);
        return professional.getReviews();
    }

    public List<ReviewEntity> getMyGivenReviewsAsAUser(){
        UserEntity user = userService.getAuthenticatedUser();
        return user.getReviewsGiven();
    }

    public double getAverageRating(Long professionalId){
        List<ReviewEntity> ratings = getReviewsForProfessional(professionalId);
        OptionalDouble average = ratings.stream().mapToInt(ReviewEntity::getScore).average();
        return average.orElse(0.0);
    }

}
