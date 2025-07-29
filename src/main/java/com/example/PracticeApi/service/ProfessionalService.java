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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;

    public ProfessionalEntity registerProfessional(ProfessionalRegisterDto dto){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(professionalRepository.existsByUser(user)){
            throw new AlreadyExistsException("Professional profile already exists for user");
        }

        ProfessionalEntity professional = new ProfessionalEntity();
        professional.setUser(user);
        professional.setFullName(dto.getFullName());
        professional.setProfession(dto.getProfession());
        professional.setLocation(dto.getLocation());
        professional.setDescription(dto.getDescription());
        professional.setPhone(dto.getPhone());

        user.setRole(Role.PROFESSIONAL);
        userRepository.save(user);

        return professionalRepository.save(professional);
    }

    public ProfessionalEntity getProfessionalById(Long id){
        return professionalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));
    }

    public List<ProfessionalEntity> getAllProfessionals(){
        return professionalRepository.findAll();
    }

    public List<ProfessionalEntity> searchProfessionals(String profession, String location){
        return professionalRepository.findByProfessionIgnoreCaseAndLocationIgnoreCase(profession, location);
    }

    public ProfessionalEntity updateProfessional(Long id, ProfessionalRegisterDto dto) {
        ProfessionalEntity professional = getProfessionalById(id);

        professional.setFullName(dto.getFullName());
        professional.setProfession(dto.getProfession());
        professional.setLocation(dto.getLocation());
        professional.setDescription(dto.getDescription());
        professional.setPhone(dto.getPhone());

        return professionalRepository.save(professional);
    }
}
