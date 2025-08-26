package com.example.PracticeApi.controller;

import com.example.PracticeApi.dto.AvailabilityBatchDto;
import com.example.PracticeApi.dto.AvailabilityDto;
import com.example.PracticeApi.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @PostMapping("/professional/me/save")
    public ResponseEntity<List<AvailabilityDto>> addAvailabilitiesForProfessional(@RequestBody AvailabilityBatchDto batchDto){
        return ResponseEntity.ok(availabilityService.saveAvailabilitiesForProfessional(batchDto.getAvailabilities()));
    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/professional/me")
    public ResponseEntity<List<AvailabilityDto>> getMyProfessionalAvailability(){
        return ResponseEntity.ok(availabilityService.getMyProfessionalAvailability());
    }

    @GetMapping("/professional/{id}")
    public ResponseEntity<List<AvailabilityDto>> getProfessionalAvailabilityById(@PathVariable Long id){
        return ResponseEntity.ok(availabilityService.getProfessionalAvailabilityById(id));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/me/save")
    public ResponseEntity<List<AvailabilityDto>> addAvailabilitiesForUser(@RequestBody AvailabilityBatchDto batchDto){
        return ResponseEntity.ok(availabilityService.saveAvailabilitiesForUser(batchDto.getAvailabilities()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/me")
    public ResponseEntity<List<AvailabilityDto>> getMyUserAvailability(){
        return ResponseEntity.ok(availabilityService.getMyUserAvailability());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AvailabilityDto>> getUserAvailabilityById(@PathVariable Long id){
        return ResponseEntity.ok(availabilityService.getUserAvailabilityById(id));
    }

}
