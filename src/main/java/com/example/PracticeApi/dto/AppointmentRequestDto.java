package com.example.PracticeApi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDto {
    @NotBlank(message = "Professional ID is required")
    private Long professionalId;
    @NotBlank(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy")
    private LocalDate date;
    @NotBlank(message = "Start time is required")
    private LocalTime startTime;
    @NotBlank(message = "End time is required")
    private LocalTime endTime;
}
