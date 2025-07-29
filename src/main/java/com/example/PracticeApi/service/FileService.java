package com.example.PracticeApi.service;

import com.example.PracticeApi.entity.FileEntity;
import com.example.PracticeApi.exception.ResourceNotFoundException;
import com.example.PracticeApi.repository.FileRepository;
import com.example.PracticeApi.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    public FileEntity getFileById(Long id){
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<FileEntity> getAllFiles(){
        return fileRepository.findAll();
    }

    public void deleteFileById(Long id){
        if(!fileRepository.existsById(id)){
            throw new ResourceNotFoundException();
        }
        fileRepository.deleteById(id);
    }
}
