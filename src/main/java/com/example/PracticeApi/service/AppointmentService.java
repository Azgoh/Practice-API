package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.AppointmentRequestDto;
import com.example.PracticeApi.dto.AppointmentResponseDto;
import com.example.PracticeApi.entity.AppointmentEntity;
import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.enumeration.AppointmentStatus;
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
    private final UserService userService;

    public AppointmentResponseDto bookAppointment(AppointmentRequestDto appointmentRequestDto) {
        UserEntity user = userService.getAuthenticatedUser();
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
        appointment.setUser(user);
        appointment.setProfessional(professional);
        appointment.setDate(appointmentRequestDto.getDate());
        appointment.setStartTime(appointmentRequestDto.getStartTime());
        appointment.setEndTime(appointmentRequestDto.getEndTime());
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);

        AppointmentEntity saved = appointmentRepository.save(appointment);

        availabilityService.updateProfessionalAvailabilityOnAppointmentBooking(appointmentRequestDto);

        return new AppointmentResponseDto(saved.getId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getStartTime(),
                appointmentRequestDto.getEndTime(),
                "Appointment booked successfully",
                AppointmentStatus.BOOKED);

    }

    public AppointmentResponseDto cancelAppointment(Long id){
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        availabilityService.updateProfessionalAvailabilityOnAppointmentCancellation(appointment);

        return new AppointmentResponseDto(appointment.getId(),
                appointment.getDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                "The appointment has been cancelled",
                appointment.getAppointmentStatus());
    }

}
