package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.ProfessionalEntity;
import com.example.PracticeApi.Entities.RatingEntity;
import com.example.PracticeApi.Entities.UserEntity;
import com.example.PracticeApi.Repositories.ProfessionalRepository;
import com.example.PracticeApi.Repositories.RatingRepository;
import com.example.PracticeApi.Repositories.UserRepository;
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
    private final UserRepository userRepository;

    public RatingEntity addOrUpdateRating(Long professionalId, Long userId, int score, String review){
        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<RatingEntity> existingRatingOpt = ratingRepository.findByProfessionalAndUser(professional, user);

        RatingEntity rating;
        if(existingRatingOpt.isPresent()){
            rating = existingRatingOpt.get();
        } else {
            rating = new RatingEntity();
            rating.setProfessional(professional);
            rating.setUser(user);
        }
        rating.setScore(score);
        rating.setReview(review);
        rating.setTimestamp(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    public List<RatingEntity> getRatingsForProfessional(Long professionalId){
        ProfessionalEntity professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));
        return ratingRepository.findByProfessional(professional);
    }

    public double getAverageRating(Long professionalId){
        List<RatingEntity> ratings = getRatingsForProfessional(professionalId);
        OptionalDouble average = ratings.stream().mapToInt(RatingEntity::getScore).average();
        return average.orElse(0.0);
    }
}
