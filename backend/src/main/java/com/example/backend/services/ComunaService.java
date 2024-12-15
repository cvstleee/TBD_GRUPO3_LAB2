package com.example.backend.services;

import com.example.backend.dtos.LocationDTO;
import com.example.backend.entities.ComunaEntity;
import com.example.backend.entities.RestrictedCommune;
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

    public Boolean isLocationRestricted(LocationDTO locationDTO) {
        return comunaRepository.isLocationRestricted(locationDTO);
    }


    //    Restricted commune

    public RestrictedCommune postRestrictedCommune(int communeId) {

        return comunaRepository.postRestrictedCommune(communeId);
    }
  
    public List<RestrictedCommune> getAllRestrictedCommune() {
        return comunaRepository.getAllRestrictedCommune();
    }

    public RestrictedCommune deleteRestrictedCommune(int communeId) {
        return comunaRepository.deleteRestrictedCommune(communeId);
    }



    public List<ComunaEntity> comunaNoRestricted(){
        return comunaRepository.comunaNoRestricted();
    }

}
