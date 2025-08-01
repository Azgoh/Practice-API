package com.example.PracticeApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String filename;
    private String contentType;

    private Long size;

}
