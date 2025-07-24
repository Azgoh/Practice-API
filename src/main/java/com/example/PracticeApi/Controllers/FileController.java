package com.example.PracticeApi.Controllers;

import com.example.PracticeApi.Entities.FileEntity;
import com.example.PracticeApi.Services.FileService;
import com.example.PracticeApi.dtos.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

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

    @PostMapping("/upload")
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        FileDto savedFile = fileService.storeFile(file);
        return ResponseEntity.ok(savedFile);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws Exception{
        FileEntity fileEntity = fileService.getFileById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                .body(fileEntity.getData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable Long id){

        return fileService.getFileById(id)
                .map(file -> new FileDto(file.getId(), file.getFilename(), file.getContentType(), (long) file.getData().length))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id){
        fileService.deleteFileById(id);
        return ResponseEntity.ok("File deleted successfully");
    }

}
