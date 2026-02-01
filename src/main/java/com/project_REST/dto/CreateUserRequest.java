package com.project_REST.dto;

import com.project_REST.entity.Role;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String id;
    private String name;
    private String email;
    private Role role;
    private String managerId;
    private String password;// 👈 comes from admin
}

