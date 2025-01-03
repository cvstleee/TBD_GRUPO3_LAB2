package com.example.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComunaEntity {
    private int id;
    private int cod_comuna;
    private String comuna;
    private String provincia;
    private String region;
    private String geom;
}
