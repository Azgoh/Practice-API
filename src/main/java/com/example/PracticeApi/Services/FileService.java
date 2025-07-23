package com.example.PracticeApi.Services;

import com.example.PracticeApi.Entities.FileEntity;
import com.example.PracticeApi.Repositories.FileRepository;
import com.example.PracticeApi.dtos.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileDto storeFile(MultipartFile file) throws IOException{
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(file.getOriginalFilename());
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());

        FileEntity saved = fileRepository.save(fileEntity);
        return new FileDto(saved.getId(), saved.getFilename(), saved.getContentType(), file.getSize());
    }

    public Optional<FileEntity> getFileById(Long id){
        if(!fileRepository.existsById(id)){
            throw new ResourceNotFoundException("File not found with id: " + id);
        }
        return fileRepository.findById(id);
    }

    public List<FileEntity> getAllFiles(){
        return fileRepository.findAll();
    }

    public void deleteFileById(Long id){
        if(!fileRepository.existsById(id)){
            throw new ResourceNotFoundException("File not found with id: " + id);
        }
        fileRepository.deleteById(id);
    }
}
