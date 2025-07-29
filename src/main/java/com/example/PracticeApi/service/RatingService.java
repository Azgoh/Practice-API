package com.example.PracticeApi.service;

import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.RatingEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.repository.ProfessionalRepository;
import com.example.PracticeApi.repository.RatingRepository;
import com.example.PracticeApi.dto.RatingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ProfessionalRepository professionalRepository;
    private final UserService userService;

    public RatingEntity addOrUpdateRating(Long professionalId, int score, String review){

        if(score < 1 || score > 5){
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));

        UserEntity user = userService.getAuthenticatedUser();

        Optional<RatingEntity> existingRatingOpt = ratingRepository.findByProfessionalAndReviewer(professional, user);

        RatingEntity rating;
        if(existingRatingOpt.isPresent()){
            rating = existingRatingOpt.get();
        } else {
            rating = new RatingEntity();
            rating.setProfessional(professional);
            rating.setReviewer(user);
        }
        rating.setScore(score);
        rating.setReview(review);
        rating.setTimestamp(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    public List<RatingEntity> getRatingsForProfessional(Long professionalId){
        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));
        return ratingRepository.findByProfessional(professional);
    }

    public double getAverageRating(Long professionalId){
        List<RatingEntity> ratings = getRatingsForProfessional(professionalId);
        OptionalDouble average = ratings.stream().mapToInt(RatingEntity::getScore).average();
        return average.orElse(0.0);
    }

    public RatingDto toDto(RatingEntity ratingEntity){
        return new RatingDto(ratingEntity.getId(),
                ratingEntity.getProfessional().getId(),
                ratingEntity.getReviewer().getId(),
                ratingEntity.getScore(),
                ratingEntity.getReview(),
                ratingEntity.getTimestamp());
    }
}
