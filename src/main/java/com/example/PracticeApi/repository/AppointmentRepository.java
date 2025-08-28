package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.AppointmentEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM AppointmentEntity a " +
            "WHERE a.professional.id = :professionalId " +
            "AND a.date = :date " +
            "AND ( (a.startTime < :endTime AND a.endTime > :startTime) )")
    boolean existsByProfessionalIdAndDateAndTimeOverlap(
            Long professionalId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );

    Optional<AppointmentEntity> findById(Long id);

    List<AppointmentEntity> findByUser(UserEntity user);

    List<AppointmentEntity> findByProfessional(ProfessionalEntity professional);

}
