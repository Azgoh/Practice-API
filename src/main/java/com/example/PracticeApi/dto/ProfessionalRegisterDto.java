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
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Profession is required")
    private String profession;
    @NotBlank(message = "Location is required")
    private String location;
    @Size(max = 100, message = "Description can be up to 100 characters")
    private String description;
    @NotBlank(message = "Hourly rate is required")
    private String hourlyRate;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 characters")
    private String phone;
}
