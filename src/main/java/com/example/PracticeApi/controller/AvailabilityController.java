package com.example.PracticeApi.controller;

import com.example.PracticeApi.dto.AvailabilityBatchDto;
import com.example.PracticeApi.dto.AvailabilityRequestDto;
import com.example.PracticeApi.dto.AvailabilityResponseDto;
import com.example.PracticeApi.dto.ExistingAvailabilityRequestDto;
import com.example.PracticeApi.service.AvailabilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
@Tag(name = "Availability Controller", description = "Endpoints for availability management")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @PostMapping("/professional/me/save")
    public ResponseEntity<AvailabilityResponseDto> saveAvailabilityForProfessional(@Valid @RequestBody AvailabilityRequestDto availabilityRequestDto){
        return ResponseEntity.ok(availabilityService.saveAvailabilityForProfessional(availabilityRequestDto));
    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/professional/edit")
    public ResponseEntity<AvailabilityResponseDto> editAvailabilitySlotForProfessional(@Valid @RequestBody ExistingAvailabilityRequestDto existingAvailabilityRequestDto){
        return ResponseEntity.ok(availabilityService.editExistingAvailabilityForProfessional(existingAvailabilityRequestDto));
    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/professional/me")
    public ResponseEntity<List<AvailabilityResponseDto>> getMyProfessionalAvailability(){
        return ResponseEntity.ok(availabilityService.getMyProfessionalAvailability());
    }

    @GetMapping("/professional/{id}")
    public ResponseEntity<List<AvailabilityResponseDto>> getProfessionalAvailabilityById(@PathVariable Long id){
        return ResponseEntity.ok(availabilityService.getProfessionalAvailabilityById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/me/save")
    public ResponseEntity<List<AvailabilityResponseDto>> addAvailabilitiesForUser(@Valid @RequestBody AvailabilityBatchDto batchDto){
        return ResponseEntity.ok(availabilityService.saveAvailabilitiesForUser(batchDto.getAvailabilities()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/me")
    public ResponseEntity<List<AvailabilityResponseDto>> getMyUserAvailability(){
        return ResponseEntity.ok(availabilityService.getMyUserAvailability());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AvailabilityResponseDto>> getUserAvailabilityById(@PathVariable Long id){
        return ResponseEntity.ok(availabilityService.getUserAvailabilityById(id));
    }

}
