package com.example.PracticeApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalRegisterDto {
    @NotBlank(message = "Full name is required")
    @Size(min = 5, max = 100, message = "Full name must be between 5 and 100 characters")
    private String fullName;
    @NotBlank(message = "Profession is required")
    private String profession;
    @NotBlank(message = "Location is required")
    private String location;
    @Size(max = 100, message = "Description can be up to 100 characters")
    private String description;
    @NotBlank(message = "Phone number is required")
    @Size(min = 7, max = 15, message = "Phone number must be between 7 and 15 characters")
    private String phone;
}
