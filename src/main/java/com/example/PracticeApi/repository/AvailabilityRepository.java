package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {

    List<AvailabilityEntity> findByUser(UserEntity user);
    List<AvailabilityEntity> findByProfessional(ProfessionalEntity professional);
    List<AvailabilityEntity> findByProfessionalIdAndDate(Long professionalId, LocalDate date);
    List<AvailabilityEntity> findByProfessionalIdOrderByDateAscStartTimeAsc(Long professionalId);

}
