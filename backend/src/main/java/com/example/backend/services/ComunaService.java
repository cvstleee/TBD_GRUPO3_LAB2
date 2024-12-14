package com.example.backend.services;

import com.example.backend.entities.ComunaEntity;
import com.example.backend.repositories.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    public List<ComunaEntity> findAllComunas() {
        return comunaRepository.findAll();
    }
}
