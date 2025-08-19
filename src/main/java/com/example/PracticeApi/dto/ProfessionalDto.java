package com.example.PracticeApi.dto;


import com.example.PracticeApi.entity.RatingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String profession;
    private String location;
    private String description;
    private String phone;
    private List<RatingEntity> ratingsReceived;
}

