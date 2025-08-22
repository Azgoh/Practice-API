package com.example.PracticeApi.mapper;

import com.example.PracticeApi.dto.ReviewDto;
import com.example.PracticeApi.dto.UserDto;
import com.example.PracticeApi.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ReviewMapper reviewMapper;

    public UserDto toDto(UserEntity user){

        List<ReviewDto> reviews = reviewMapper.toDtoList(user.getReviewsGiven());

        return new UserDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isEnabled(),
                user.getAuthProvider(),
                reviews);
    }
}
