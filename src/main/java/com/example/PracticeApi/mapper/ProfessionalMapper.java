package com.example.PracticeApi.mapper;

import com.example.PracticeApi.dto.ProfessionalDto;
import com.example.PracticeApi.dto.ReviewDto;
import com.example.PracticeApi.entity.ProfessionalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfessionalMapper {

    private final ReviewMapper reviewMapper;

    public ProfessionalDto toDto(ProfessionalEntity professional){

        List<ReviewDto> reviews = reviewMapper.toDtoList(professional.getReviewsReceived());

        return new ProfessionalDto(professional.getId(),
                professional.getFirstName(),
                professional.getLastName(),
                professional.getProfession(),
                professional.getLocation(),
                professional.getDescription(),
                professional.getPhone(),
                reviews);
    }
}
