package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateDTO {
    private String state;
    private double total;
    private Date shipping_date;
    private double latitude;
    private double longitude;
}
