package com.example.PracticeApi.Controllers;

import com.example.PracticeApi.Entities.ProfessionalEntity;
import com.example.PracticeApi.Services.ProfessionalService;
import com.example.PracticeApi.dtos.ProfessionalRegisterDto;
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
    public ResponseEntity<ProfessionalEntity> getProfessionalById(@PathVariable Long id){
        return ResponseEntity.ok(professionalService.getProfessionalById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalEntity>> getAllProfessionals(){
        return ResponseEntity.ok(professionalService.getAllProfessionals());
    }
}
