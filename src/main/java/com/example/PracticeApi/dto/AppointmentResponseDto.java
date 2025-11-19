package com.example.PracticeApi.dto;

import com.example.PracticeApi.enumeration.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {

    private Long appointmentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "d MMMM yyyy")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    private String message;

    private AppointmentStatus appointmentStatus;

    private Long professionalId;
    private String professionalName;

    private Long userId;
    private String userName;
}
