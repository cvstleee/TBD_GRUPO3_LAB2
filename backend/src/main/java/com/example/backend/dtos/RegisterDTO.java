package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private Double latitude;
    private Double longitude;
}
