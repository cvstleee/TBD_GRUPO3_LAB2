package com.example.backend.services;

import com.example.backend.entities.DistributorEntity;
import com.example.backend.repositories.DistributorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorService {
    @Autowired
    DistributorRepository distributorRepository;

    public List<DistributorEntity> getAllDistributors() {
        return distributorRepository.findAll();
    }

    public List<DistributorEntity> getDistributorsByComuna(int comunaId) {
        return distributorRepository.findDistributorsByComuna(comunaId);
    }
}
