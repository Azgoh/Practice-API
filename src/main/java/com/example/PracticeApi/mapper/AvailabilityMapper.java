package com.example.PracticeApi.mapper;

import com.example.PracticeApi.dto.AvailabilityDto;
import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class AvailabilityMapper {

    public AvailabilityDto toDto(AvailabilityEntity availabilityEntity){
        return new AvailabilityDto(
                availabilityEntity.getDate(),
                availabilityEntity.getDayOfWeek(),
                availabilityEntity.getStartTime(),
                availabilityEntity.getEndTime()
        );
    }

    public AvailabilityEntity toEntity(AvailabilityDto availabilityDto, UserEntity user){
        AvailabilityEntity availabilityEntity = new AvailabilityEntity();
        availabilityEntity.setUser(user);
        availabilityEntity.setDate(availabilityDto.getDate());
        availabilityEntity.setDayOfWeek(availabilityDto.getDayOfWeek());
        availabilityEntity.setStartTime(availabilityDto.getStartTime());
        availabilityEntity.setEndTime(availabilityDto.getEndTime());
        return availabilityEntity;
    }

    public AvailabilityEntity toEntityForProfessional(AvailabilityDto availabilityDto, ProfessionalEntity professional){
        AvailabilityEntity availabilityEntity = new AvailabilityEntity();
        availabilityEntity.setProfessional(professional);
        availabilityEntity.setDate(availabilityDto.getDate());
        availabilityEntity.setDayOfWeek(availabilityDto.getDayOfWeek());
        availabilityEntity.setStartTime(availabilityDto.getStartTime());
        availabilityEntity.setEndTime(availabilityDto.getEndTime());
        return availabilityEntity;
    }
}
