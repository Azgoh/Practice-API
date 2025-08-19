package com.example.PracticeApi.controller;

import com.example.PracticeApi.dto.ProfessionalDto;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.service.ProfessionalService;
import com.example.PracticeApi.dto.ProfessionalRegisterDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Professional Controller", description = "Endpoints for managing professionals")
@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {
    private final ProfessionalService professionalService;

    @PostMapping("/register")
    public ResponseEntity<String> registerProfessional(@Valid @RequestBody ProfessionalRegisterDto dto){
        professionalService.registerProfessional(dto);
        return ResponseEntity.ok("Professional profile created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalDto> getProfessionalById(@PathVariable Long id){
        ProfessionalEntity professional = professionalService.getProfessionalById(id);
        ProfessionalDto dto = professionalService.toDto(professional);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<ProfessionalDto> getProfessionalByJwt(){
        ProfessionalEntity professional = professionalService.getProfessionalByJwt();
        ProfessionalDto dto = professionalService.toDto(professional);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<ProfessionalDto>> getAllProfessionals(){
        List<ProfessionalEntity> professionals = professionalService.getAllProfessionals();
        List<ProfessionalDto> dtos = professionals.stream()
                .map(prof -> professionalService.toDto(prof))
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
