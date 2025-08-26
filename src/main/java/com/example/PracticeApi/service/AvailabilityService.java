package com.example.PracticeApi.service;

import com.example.PracticeApi.dto.AppointmentRequestDto;
import com.example.PracticeApi.dto.AvailabilityDto;
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

    public List<AvailabilityDto> saveAvailabilitiesForProfessional(List<AvailabilityDto> dtos){
        ProfessionalEntity professional = userService.getAuthenticatedProfessional();
        List<AvailabilityEntity> availabilityEntities = dtos.stream()
                .map(dto -> availabilityMapper.toEntityForProfessional(dto, professional))
                .toList();
        List<AvailabilityEntity> savedEntities = availabilityRepository.saveAll(availabilityEntities);

        return savedEntities.stream().map(availabilityMapper::toDto).toList();
    }

    public List<AvailabilityDto> getMyProfessionalAvailability(){
        ProfessionalEntity professional = userService.getAuthenticatedProfessional();
        return availabilityRepository.findByProfessionalIdOrderByDateAscStartTimeAsc(professional.getId())
                .stream()
                .map(availabilityMapper::toDto)
                .toList();
    }

    public List<AvailabilityDto> getAnyProfessionalAvailability(ProfessionalEntity professional){
        return availabilityRepository.findByProfessionalIdOrderByDateAscStartTimeAsc(professional.getId()).stream()
                .map(availabilityMapper::toDto)
                .toList();
    }

    public void updateProfessionalAvailabilityOnAppointmentEvent(AppointmentRequestDto appointmentRequestDto,
                                                                 AppointmentEntity appointment){
        List<AvailabilityEntity> availabilitiesForAppointmentDay = availabilityRepository
                .findByProfessionalIdAndDate(appointmentRequestDto.getProfessionalId(), appointmentRequestDto.getDate());

        LocalTime appointmentStartTime = appointment.getStartTime();
        LocalTime appointmentEndTime = appointment.getEndTime();

        List<AvailabilityEntity> updatedAvailabilities = new ArrayList<>();

        for(AvailabilityEntity slot : availabilitiesForAppointmentDay){
            LocalTime slotStartTime = slot.getStartTime();
            LocalTime slotEndTime = slot.getEndTime();

            // Appointment start > slot start && appointment end < slot end

            if(appointmentStartTime.isAfter(slotStartTime) && appointmentEndTime.isBefore(slotEndTime)){
                AvailabilityEntity before = new AvailabilityEntity(null, null, slot.getProfessional(),
                        appointmentRequestDto.getDate(), slotStartTime, appointmentStartTime);
                AvailabilityEntity after = new AvailabilityEntity(null, null, slot.getProfessional(),
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

    public List<AvailabilityEntity> findAvailabilityByProfessionalIdAndDate(Long professionalId, LocalDate date){
        return availabilityRepository.findByProfessionalIdAndDate(professionalId, date);
    }

    // Might be useful for filtering professionals depending on the overlapping availability with the user.

    public List<AvailabilityDto> saveAvailabilitiesForUser(List<AvailabilityDto> dtos){
        UserEntity user = userService.getAuthenticatedUser();

        List<AvailabilityEntity> availabilityEntities = dtos.stream()
                .map(dto -> availabilityMapper.toEntity(dto, user))
                .toList();

        List<AvailabilityEntity> savedEntities = availabilityRepository.saveAll(availabilityEntities);

        return savedEntities.stream().map(availabilityMapper::toDto).toList();
    }

    public List<AvailabilityDto> getMyUserAvailability(){
        UserEntity user = userService.getAuthenticatedUser();
        return availabilityRepository.findByUser(user).stream()
                .map(availabilityMapper::toDto)
                .toList();
    }

    public List<AvailabilityDto> getAnyUserAvailability(UserEntity user){
        return availabilityRepository.findByUser(user).stream()
                .map(availabilityMapper::toDto)
                .toList();
    }
}
