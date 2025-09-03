package com.example.PracticeApi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityBatchDto {

    @Valid
    @NotEmpty(message = "At least one availability is required")
    private List<AvailabilityRequestDto> availabilities;
}
