package com.example.PracticeApi.dto;

import com.example.PracticeApi.entity.RatingEntity;
import com.example.PracticeApi.enumeration.AuthProvider;
import com.example.PracticeApi.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithProfessionalDto {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private boolean enabled;
    private AuthProvider authProvider;
    private ProfessionalDto professionalProfile;

}
