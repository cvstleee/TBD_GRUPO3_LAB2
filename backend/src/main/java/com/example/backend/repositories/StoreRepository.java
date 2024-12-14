package com.example.backend.repositories;

import com.example.backend.entities.DistributorEntity;
import com.example.backend.entities.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class StoreRepository {
    @Autowired
    private Sql2o sql2o;

    public List<StoreEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM stores")
                    .executeAndFetch(StoreEntity.class);
        }
    }
}
