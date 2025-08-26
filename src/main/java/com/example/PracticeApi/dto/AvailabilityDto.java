package com.example.PracticeApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    @NotNull(message = "Date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy")
    private LocalDate date;
    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @AssertTrue(message = "Start time must be before end time")
    @JsonIgnore
    public boolean isStartBeforeEnd() {
        return startTime != null && endTime != null && startTime.isBefore(endTime);
    }

}
