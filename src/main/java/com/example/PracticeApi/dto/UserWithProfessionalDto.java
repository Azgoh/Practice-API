package com.example.PracticeApi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithProfessionalDto {

    private UserDto userProfile;
    private ProfessionalDto professionalProfile;

}
