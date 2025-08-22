package com.example.PracticeApi.entity;


import com.example.PracticeApi.enumeration.AuthProvider;
import com.example.PracticeApi.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Column(nullable = false)
    private boolean enabled;

    private String verificationToken;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-review")
    private List<ReviewEntity> reviewsGiven;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfessionalEntity professionalEntity;
}
