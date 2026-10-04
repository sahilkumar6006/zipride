package com.zipride.dto.request;

import com.zipride.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private UserRole role;
    private String licenseNumber;   // required when role = DRIVER
}
