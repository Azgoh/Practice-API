package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
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

}
