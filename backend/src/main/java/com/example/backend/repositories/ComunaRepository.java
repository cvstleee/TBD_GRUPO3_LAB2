package com.example.backend.repositories;

import com.example.backend.entities.ComunaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ComunaRepository {
    @Autowired
    private Sql2o sql2o;

    public List<ComunaEntity> findAll(){
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT id, comuna FROM comunas_santiago")
                    .executeAndFetch(ComunaEntity.class);
        }
    }
}
