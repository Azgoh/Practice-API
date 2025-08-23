//package com.example.PracticeApi.controller;
//
//import com.example.PracticeApi.dto.AvailabilityDto;
//import com.example.PracticeApi.service.AvailabilityService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/availability")
//@RequiredArgsConstructor
//public class AvailabilityController {
//    private final AvailabilityService availabilityService;
//
//    @PostMapping("/users/me")
//    public ResponseEntity<List<AvailabilityDto>> addAvailabilitiesForUser(@RequestBody List<AvailabilityDto> availabilities){
//        return ResponseEntity.ok(availabilityService.saveAvailabilitiesForUser(availabilities));
//    }
//
//    @GetMapping()
//}
