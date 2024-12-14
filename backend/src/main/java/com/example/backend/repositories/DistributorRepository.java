package com.example.backend.repositories;

import com.example.backend.entities.ClientEntity;
import com.example.backend.entities.DistributorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DistributorRepository {
    @Autowired
    private Sql2o sql2o;

    public List<DistributorEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM distributors")
                    .executeAndFetch(DistributorEntity.class);
        }
    }

    public List<DistributorEntity> findDistributorsByComuna(int idComuna) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT DISTINCT d.id, d.name FROM orders o " +
                            "JOIN distributors d ON o.distributor_id = d.id " +
                            "JOIN comunas_santiago c ON ST_Contains(c.geom, o.delivery_location) " +
                            "WHERE c.id = :idComuna")
                    .addParameter("idComuna", idComuna)
                    .executeAndFetch(DistributorEntity.class);
        }
    }

}
