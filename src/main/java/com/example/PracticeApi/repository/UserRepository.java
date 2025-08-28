package com.example.PracticeApi.repository;

import com.example.PracticeApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u from UserEntity u WHERE u.username = :identifier OR u.email = :identifier")
    Optional<UserEntity> findByUsernameOrEmail(@Param("identifier") String identifier);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByVerificationToken(String token);
}
