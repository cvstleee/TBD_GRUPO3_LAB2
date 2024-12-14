package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private int id;
    private Timestamp order_date;
    private String state;
    private int client_id;
    private int distributor_id;
    private double total;
    private Date shipping_date;
    private double latitude;
    private double longitude;
    private String delivery_location;
    private Timestamp deleted_at;
}
