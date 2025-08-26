package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.ProfessionalDto;
import com.example.PracticeApi.dto.UserDto;
import com.example.PracticeApi.dto.UserWithProfessionalDto;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.enumeration.Role;
import com.example.PracticeApi.mapper.ProfessionalMapper;
import com.example.PracticeApi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserService userService;
    private final ProfessionalService professionalService;
    private final UserMapper userMapper;
    private final ProfessionalMapper professionalMapper;

    public UserWithProfessionalDto getCurrentUserWithProfessional() {
        UserEntity user = userService.getAuthenticatedUser();
        UserDto userDto = userMapper.toDto(user);

        ProfessionalDto profDto = null;
        if (user.getRole() == Role.PROFESSIONAL) {
            ProfessionalEntity professional = professionalService.getAuthenticatedProfessional();
            if (professional != null) {
                profDto = professionalMapper.toDto(professional);
            }
        }

        return new UserWithProfessionalDto(userDto, profDto);
    }
}
