package com.example.PracticeApi.controller;

import com.example.PracticeApi.dto.AppointmentRequestDto;
import com.example.PracticeApi.dto.AppointmentResponseDto;
import com.example.PracticeApi.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment Controller", description = "Endpoints for appointment management")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@Valid @RequestBody AppointmentRequestDto request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(request));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @GetMapping("/get-appointment/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PreAuthorize("hasAnyRole('USER', 'PROFESSIONAL', 'ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponseDto>> getMyAppointments() {
        return ResponseEntity.ok(appointmentService.getMyAppointments());
    }
}