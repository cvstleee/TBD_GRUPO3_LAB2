package com.example.backend.controllers;

import com.example.backend.entities.ComunaEntity;
import com.example.backend.services.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/comunas")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaEntity>> getAllComunas(){
        return new ResponseEntity<>(comunaService.findAllComunas(), HttpStatus.OK);
    }
}
