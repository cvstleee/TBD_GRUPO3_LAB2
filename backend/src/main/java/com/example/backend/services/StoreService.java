package com.example.backend.services;

import com.example.backend.entities.StoreEntity;
import com.example.backend.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreRepository storeRepository;

    public List<StoreEntity> getAllStores() {
        return storeRepository.findAll();
    }

}
