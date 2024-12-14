package com.example.backend.controllers;

import com.example.backend.entities.StoreEntity;
import com.example.backend.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/stores")
public class StoreController {
    @Autowired
    StoreService storeService;

    @GetMapping
    public ResponseEntity<List<StoreEntity>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

}
