package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.RatingEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByProfessional(ProfessionalEntity professional);

    Optional<RatingEntity> findByProfessionalAndReviewer(ProfessionalEntity professional, UserEntity user);

}
