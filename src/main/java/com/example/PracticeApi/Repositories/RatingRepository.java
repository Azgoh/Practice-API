package com.example.PracticeApi.Repositories;

import com.example.PracticeApi.Entities.ProfessionalEntity;
import com.example.PracticeApi.Entities.RatingEntity;
import com.example.PracticeApi.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByProfessional(ProfessionalEntity professional);

    Optional<RatingEntity> findByProfessionalAndReviewer(ProfessionalEntity professional, UserEntity user);

}
