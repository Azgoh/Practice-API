//package com.example.PracticeApi.service;
//
//import com.example.PracticeApi.dto.AvailabilityDto;
//import com.example.PracticeApi.entity.AvailabilityEntity;
//import com.example.PracticeApi.entity.ProfessionalEntity;
//import com.example.PracticeApi.entity.UserEntity;
//import com.example.PracticeApi.mapper.AvailabilityMapper;
//import com.example.PracticeApi.repository.AvailabilityRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AvailabilityService {
//    private final AvailabilityRepository availabilityRepository;
//    private final AvailabilityMapper availabilityMapper;
//    private final UserService userService;
//
//    public List<AvailabilityDto> saveAvailabilitiesForUser(List<AvailabilityDto> dtos){
//        UserEntity user = userService.getAuthenticatedUser();
//
//        List<AvailabilityEntity> availabilityEntities = dtos.stream()
//                .map(dto -> availabilityMapper.toEntity(dto, user))
//                .toList();
//
//        List<AvailabilityEntity> savedEntities = availabilityRepository.saveAll(availabilityEntities);
//
//        return savedEntities.stream().map(availabilityMapper::toDto).toList();
//    }
//
//    public List<AvailabilityDto> saveAvailabilitiesForProfessional(List<AvailabilityDto> dtos){
//        ProfessionalEntity professional = userService.getAuthenticatedProfessional();
//
//    }
//
//    public List<AvailabilityDto> getMyUserAvailability(){
//        UserEntity user = userService.getAuthenticatedUser();
//        return availabilityRepository.findByUser(user).stream()
//                .map(availabilityMapper::toDto)
//                .toList();
//    }
//}
