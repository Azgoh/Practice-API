package com.example.PracticeApi.mapper;

import com.example.PracticeApi.dto.AvailabilityRequestDto;
import com.example.PracticeApi.dto.AvailabilityResponseDto;
import com.example.PracticeApi.entity.AvailabilityEntity;
import com.example.PracticeApi.entity.ProfessionalEntity;
import com.example.PracticeApi.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AvailabilityMapper {

    public AvailabilityResponseDto toAvailabilityResponseDto(AvailabilityEntity availabilityEntity){
        return new AvailabilityResponseDto(
                availabilityEntity.getId(),
                availabilityEntity.getDate(),
                availabilityEntity.getStartTime(),
                availabilityEntity.getEndTime()
        );
    }

    public AvailabilityEntity toEntity(AvailabilityRequestDto availabilityRequestDto, UserEntity user){
        AvailabilityEntity availabilityEntity = new AvailabilityEntity();
        availabilityEntity.setUser(user);
        availabilityEntity.setDate(availabilityRequestDto.getDate());
        availabilityEntity.setStartTime(availabilityRequestDto.getStartTime());
        availabilityEntity.setEndTime(availabilityRequestDto.getEndTime());
        return availabilityEntity;
    }

    public AvailabilityEntity toEntityForProfessional(AvailabilityRequestDto availabilityRequestDto, ProfessionalEntity professional){
        AvailabilityEntity availabilityEntity = new AvailabilityEntity();
        availabilityEntity.setProfessional(professional);
        availabilityEntity.setDate(availabilityRequestDto.getDate());
        availabilityEntity.setStartTime(availabilityRequestDto.getStartTime());
        availabilityEntity.setEndTime(availabilityRequestDto.getEndTime());
        return availabilityEntity;
    }
}
