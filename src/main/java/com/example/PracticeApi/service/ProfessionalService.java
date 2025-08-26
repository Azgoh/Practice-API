package com.example.PracticeApi.service;


import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.exception.AlreadyExistsException;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.repository.ProfessionalRepository;
import com.example.PracticeApi.repository.UserRepository;
import com.example.PracticeApi.dto.ProfessionalRegisterDto;
import com.example.PracticeApi.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final UserService userService;

    public ProfessionalEntity registerProfessional(ProfessionalRegisterDto dto) {
        UserEntity user = userService.getAuthenticatedUser();

        if (professionalRepository.existsByUser(user)) {
            throw new AlreadyExistsException("Professional profile already exists for user");
        }

        ProfessionalEntity professional = new ProfessionalEntity();
        professional.setUser(user);
        professional.setFirstName(dto.getFirstName());
        professional.setLastName(dto.getLastName());
        professional.setProfession(dto.getProfession());
        professional.setLocation(dto.getLocation());
        professional.setDescription(dto.getDescription());
        professional.setPhone(dto.getPhone());
        professional.setHourlyRate(dto.getHourlyRate());

        userService.updateUserRole(user, Role.PROFESSIONAL);

        return professionalRepository.save(professional);
    }

    public ProfessionalEntity getAuthenticatedProfessional() {
        UserEntity user = userService.getAuthenticatedUser();
        return professionalRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));
    }

    public ProfessionalEntity getProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));
    }

    public List<ProfessionalEntity> getAllProfessionals() {
        return professionalRepository.findAll();
    }

    public List<ProfessionalEntity> searchProfessionals(String profession, String location) {
        return professionalRepository.findByProfessionIgnoreCaseAndLocationIgnoreCase(profession, location);
    }

    public ProfessionalEntity updateProfessional(Long id, ProfessionalRegisterDto dto) {
        ProfessionalEntity professional = getProfessionalById(id);

        professional.setFirstName(dto.getFirstName());
        professional.setLastName(dto.getLastName());
        professional.setProfession(dto.getProfession());
        professional.setLocation(dto.getLocation());
        professional.setDescription(dto.getDescription());
        professional.setPhone(dto.getPhone());
        professional.setHourlyRate(dto.getHourlyRate());

        return professionalRepository.save(professional);
    }

}
