package com.example.springexercise3boot.dto;

import com.example.springexercise3boot.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
    private int id;
    private String username;
    private String password;
    private Role role;
    private String name;
    private String email;
}
