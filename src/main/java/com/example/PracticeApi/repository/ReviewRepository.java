package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.ReviewEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByProfessional(ProfessionalEntity professional);

    Optional<ReviewEntity> findByProfessionalAndReviewer(ProfessionalEntity professional, UserEntity user);

}
