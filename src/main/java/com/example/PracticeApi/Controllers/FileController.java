package com.example.PracticeApi.Controllers;

import com.example.PracticeApi.Entities.FileEntity;
import com.example.PracticeApi.Services.FileService;
import com.example.PracticeApi.dtos.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Controller", description = "Endpoints for file management")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Operation(summary = "Returns a list of all files")
    @GetMapping
    public ResponseEntity<List<FileDto>> getAllFiles(){
        List<FileEntity> files = fileService.getAllFiles();
        List<FileDto> filesDto = files.stream()
                .map(file -> new FileDto(file.getId(),
                        file.getFilename(),
                        file.getContentType(),
                        (long) file.getData().length))
                .toList();
        return ResponseEntity.ok(filesDto);
    }

    @Operation(summary = "Upload a file")
    @PostMapping("/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        FileDto savedFile = fileService.storeFile(file);
        return ResponseEntity.ok(savedFile);

    }

    @Operation(summary = "Download a file based on its id")
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws Exception{
        FileEntity fileEntity = fileService.getFileById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                .body(fileEntity.getData());
    }


    @Operation(summary = "Get a file based on its id")
    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable Long id){
        FileEntity file = fileService.getFileById(id);
        FileDto dto = new FileDto(
                file.getId(),
                file.getFilename(),
                file.getContentType(),
                (long) file.getData().length
        );

        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Delete a file based on its id. Must be an admin")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id){
        fileService.deleteFileById(id);
        return ResponseEntity.ok("File deleted successfully");
    }

}
