package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreEntity {
    private int id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String location;
}
