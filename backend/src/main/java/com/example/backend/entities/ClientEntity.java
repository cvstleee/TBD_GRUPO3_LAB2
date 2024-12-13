package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Double latitude;
    private Double longitude;
    private String location;
    private Timestamp deleted_at;
}
