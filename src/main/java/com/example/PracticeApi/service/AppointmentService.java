package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.AppointmentRequestDto;
import com.example.PracticeApi.dto.AppointmentResponseDto;
import com.example.PracticeApi.entity.AppointmentEntity;
import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityService availabilityService;
    private final ProfessionalService professionalService;

    public AppointmentResponseDto bookAppointment(AppointmentRequestDto appointmentRequestDto) {
        ProfessionalEntity professional = professionalService.getProfessionalById(appointmentRequestDto.getProfessionalId());
        List<AvailabilityEntity> availabilities = availabilityService.findAvailabilityByProfessionalIdAndDate(
                appointmentRequestDto.getProfessionalId(), appointmentRequestDto.getDate()
        );
        if (availabilities.isEmpty()) {
            throw new RuntimeException("No availability for this date");
        }
        boolean fits = availabilities.stream().anyMatch(a ->
                !appointmentRequestDto.getStartTime().isBefore(a.getStartTime()) &&
                        !appointmentRequestDto.getEndTime().isAfter(a.getEndTime())
        );

        if (!fits) {
            throw new RuntimeException("Requested time is outside professional's availability");
        }

        boolean conflict = appointmentRepository.existsByProfessionalIdAndDateAndTimeOverlap(
                appointmentRequestDto.getProfessionalId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getStartTime(),
                appointmentRequestDto.getEndTime()
        );

        if (conflict) {
            throw new RuntimeException("Time slot already booked");
        }

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setUser(professional.getUser());
        appointment.setProfessional(professional);
        appointment.setDate(appointmentRequestDto.getDate());
        appointment.setStartTime(appointmentRequestDto.getStartTime());
        appointment.setEndTime(appointmentRequestDto.getEndTime());

        AppointmentEntity saved = appointmentRepository.save(appointment);

        availabilityService.updateProfessionalAvailabilityOnAppointmentEvent(appointmentRequestDto, saved);

        return new AppointmentResponseDto(saved.getId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getStartTime(),
                appointmentRequestDto.getEndTime(),
                "Appointment booked successfully");

    }

    public AppointmentEntity findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }
}
