package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    Optional<ProfessionalEntity> findByUserId(Long id);

    List<ProfessionalEntity> findByProfessionIgnoreCase(String profession);
    List<ProfessionalEntity> findByProfessionIgnoreCaseAndLocationIgnoreCase(String profession, String location);

    boolean existsByUser(UserEntity user);
}
