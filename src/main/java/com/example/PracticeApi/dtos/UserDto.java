package com.example.PracticeApi.dtos;

import com.example.PracticeApi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private boolean enabled;

}
