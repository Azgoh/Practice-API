package com.example.PracticeApi.Repositories;

import com.example.PracticeApi.Entities.ProfessionalEntity;
import com.example.PracticeApi.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    Optional<ProfessionalEntity> findByUserId(Long id);
    List<ProfessionalEntity> findByProfessionIgnoreCase(String profession);
    List<ProfessionalEntity> findByProfessionIgnoreCaseAndLocationIgnoreCase(String profession, String location);

    boolean existsByUser(UserEntity user);
}
