package com.example.PracticeApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    @NotBlank(message = "Date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy")
    private LocalDate date;
    @NotBlank(message = "Day of the week is required")
    private DayOfWeek dayOfWeek;
    @NotBlank(message = "Start time is required")
    private LocalTime startTime;
    @NotBlank(message = "End time is required")
    private LocalTime endTime;
}
