package com.example.PracticeApi.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name ="professional_profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String profession;
    @Column(nullable = false)
    private String location;
    private String description;
    @Column(nullable = false, unique = true)
    private String phone;
    @OneToOne
    @JoinColumn(name="user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    private List<RatingEntity> ratings;
}
