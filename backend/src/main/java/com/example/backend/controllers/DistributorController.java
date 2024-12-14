package com.example.backend.controllers;

import com.example.backend.entities.DistributorEntity;
import com.example.backend.services.DistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/distributors")
public class DistributorController {
    @Autowired
    DistributorService distributorService;

    @GetMapping
    public ResponseEntity<List<DistributorEntity>> getDistributors() {
        return new ResponseEntity<>(distributorService.getAllDistributors(), HttpStatus.OK);
    }

    @GetMapping("/getByComuna/{comunaId}")
    public List<DistributorEntity> getDistributorsByComuna(@PathVariable int comunaId) {
        return distributorService.getDistributorsByComuna(comunaId);
    }
}
