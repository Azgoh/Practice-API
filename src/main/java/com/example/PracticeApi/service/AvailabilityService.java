package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.AppointmentRequestDto;
import com.example.PracticeApi.dto.AvailabilityRequestDto;
import com.example.PracticeApi.dto.AvailabilityResponseDto;
import com.example.PracticeApi.entity.AppointmentEntity;
import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import com.example.PracticeApi.mapper.AvailabilityMapper;
import com.example.PracticeApi.repository.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;
    private final UserService userService;
    private final ProfessionalService professionalService;

    public List<AvailabilityResponseDto> saveAvailabilitiesForProfessional(List<AvailabilityRequestDto> dtos){
        ProfessionalEntity professional = professionalService.getAuthenticatedProfessional();
        List<AvailabilityEntity> availabilityEntities = dtos.stream()
                .map(dto -> availabilityMapper.toEntityForProfessional(dto, professional))
                .toList();
        List<AvailabilityEntity> savedEntities = availabilityRepository.saveAll(availabilityEntities);

        return savedEntities.stream().map(availabilityMapper::toAvailabilityResponseDto).toList();
    }

    public List<AvailabilityResponseDto> getMyProfessionalAvailability(){
        ProfessionalEntity professional = professionalService.getAuthenticatedProfessional();
        return availabilityRepository.findByProfessionalIdOrderByDateAscStartTimeAsc(professional.getId())
                .stream()
                .map(availabilityMapper::toAvailabilityResponseDto)
                .toList();
    }

    public List<AvailabilityResponseDto> getProfessionalAvailabilityById(Long professionalId){
        return availabilityRepository.findByProfessionalIdOrderByDateAscStartTimeAsc(professionalId).stream()
                .map(availabilityMapper::toAvailabilityResponseDto)
                .toList();
    }

    public void updateProfessionalAvailabilityOnAppointmentBooking(AppointmentRequestDto appointmentRequestDto){
        List<AvailabilityEntity> availabilitiesForAppointmentDay = availabilityRepository
                .findByProfessionalIdAndDate(appointmentRequestDto.getProfessionalId(), appointmentRequestDto.getDate());

        LocalTime appointmentStartTime = appointmentRequestDto.getStartTime();
        LocalTime appointmentEndTime = appointmentRequestDto.getEndTime();

        List<AvailabilityEntity> updatedAvailabilities = new ArrayList<>();

        for(AvailabilityEntity slot : availabilitiesForAppointmentDay){
            LocalTime slotStartTime = slot.getStartTime();
            LocalTime slotEndTime = slot.getEndTime();

            // Appointment start > slot start && appointment end < slot end

            if(appointmentStartTime.isAfter(slotStartTime) && appointmentEndTime.isBefore(slotEndTime)){
                AvailabilityEntity before = new AvailabilityEntity(null, null, slot.getProfessional(), "_",
                        appointmentRequestDto.getDate(), slotStartTime, appointmentStartTime);
                AvailabilityEntity after = new AvailabilityEntity(null, null, slot.getProfessional(), "_",
                        appointmentRequestDto.getDate(), appointmentEndTime, slotEndTime);
                availabilityRepository.delete(slot);
                updatedAvailabilities.addAll(List.of(before, after));
            }

            // Appointment start = slot start && appointment end < slot end

            else if(appointmentStartTime.equals(slotStartTime) && appointmentEndTime.isBefore(slotEndTime)){
                slot.setStartTime(appointmentEndTime);
                updatedAvailabilities.add(slot);
            }

            // Appointment start > slot start && appointment end = slot end

            else if(appointmentStartTime.isAfter(slotStartTime) && appointmentEndTime.equals(slotEndTime)){
                slot.setEndTime(appointmentStartTime);
                updatedAvailabilities.add(slot);
            }

            // Appointment start = slot start && appointment end = slot end

            else if(appointmentStartTime.equals(slotStartTime) && appointmentEndTime.equals(slotEndTime)){
                availabilityRepository.delete(slot);
            }

            // Appointment doesn't fall in this slot

            else{
                updatedAvailabilities.add(slot);
            }

        }

        availabilityRepository.saveAll(updatedAvailabilities);

    }

    public void updateProfessionalAvailabilityOnAppointmentCancellation(AppointmentEntity appointment){
        LocalDate appointmentDate = appointment.getDate();
        LocalTime appointmentStartTime = appointment.getStartTime();
        LocalTime appointmentEndTime = appointment.getEndTime();
        Long professionalId = appointment.getProfessional().getId();

        List<AvailabilityEntity> availabilitiesForAppointmentDay = availabilityRepository
                .findByProfessionalIdAndDate(professionalId, appointmentDate);

        // Find slot ending right before appointment start

        AvailabilityEntity left = availabilitiesForAppointmentDay.stream()
                .filter(a -> a.getEndTime().equals(appointmentStartTime))
                .findFirst()
                .orElse(null);

        // Find slot starting right after appointment end

        AvailabilityEntity right = availabilitiesForAppointmentDay.stream()
                .filter(a -> a.getStartTime().equals(appointmentEndTime))
                .findFirst()
                .orElse(null);

        // Check if both neighbours are touched and extend left slot + appointment + right slot, delete right slot

        if (left != null && right != null) {
            left.setEndTime(right.getEndTime());
            availabilityRepository.delete(right);
        }

        // Check if only left slot is touched, extend left slot + appointment

        else if (left != null) {
            left.setEndTime(appointmentEndTime);
        }

        // Check if only right slot is touched, extend appointment + right slot

        else if (right != null) {
            right.setStartTime(appointmentStartTime);
        }

        // If both left and right are null, create new availability slot

        else {
            AvailabilityEntity restored = new AvailabilityEntity();
            restored.setDate(appointmentDate);
            restored.setStartTime(appointmentStartTime);
            restored.setEndTime(appointmentEndTime);
            restored.setProfessional(appointment.getProfessional());
            availabilityRepository.save(restored);
            return;
        }

        availabilityRepository.flush();

    }

    public List<AvailabilityEntity> findAvailabilityByProfessionalIdAndDate(Long professionalId, LocalDate date){
        return availabilityRepository.findByProfessionalIdAndDate(professionalId, date);
    }

    // Might be useful for filtering professionals depending on the overlapping availability with the user.

    public List<AvailabilityResponseDto> saveAvailabilitiesForUser(List<AvailabilityRequestDto> dtos){
        UserEntity user = userService.getAuthenticatedUser();

        List<AvailabilityEntity> availabilityEntities = dtos.stream()
                .map(dto -> availabilityMapper.toEntity(dto, user))
                .toList();

        List<AvailabilityEntity> savedEntities = availabilityRepository.saveAll(availabilityEntities);

        return savedEntities.stream().map(availabilityMapper::toAvailabilityResponseDto).toList();
    }

    public List<AvailabilityResponseDto> getMyUserAvailability(){
        UserEntity user = userService.getAuthenticatedUser();
        return availabilityRepository.findByUser(user).stream()
                .map(availabilityMapper::toAvailabilityResponseDto)
                .toList();
    }

    public List<AvailabilityResponseDto> getUserAvailabilityById(Long userId){
        return availabilityRepository.findByUserIdOrderByDateAscStartTimeAsc(userId).stream()
                .map(availabilityMapper::toAvailabilityResponseDto)
                .toList();
    }
}
