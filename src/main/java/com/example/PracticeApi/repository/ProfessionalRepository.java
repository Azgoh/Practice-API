package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {

    List<ProfessionalEntity> findByProfessionIgnoreCase(String profession);
    List<ProfessionalEntity> findByProfessionIgnoreCaseAndLocationIgnoreCase(String profession, String location);

    Optional<ProfessionalEntity> findByUser(UserEntity user);
    boolean existsByUser(UserEntity user);
}
