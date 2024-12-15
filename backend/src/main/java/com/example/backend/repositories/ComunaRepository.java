package com.example.backend.repositories;

import com.example.backend.dtos.LocationDTO;
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

    public boolean isLocationRestricted(LocationDTO locationDTO) {
        String query = """
            WITH input_location AS (
                SELECT ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) AS location_geom
            )
            SELECT 
                CASE 
                    WHEN rc.id IS NOT NULL THEN TRUE
                    ELSE FALSE
                END AS is_restricted
            FROM 
                input_location il
            LEFT JOIN 
                comunas_santiago cs ON ST_Within(il.location_geom, cs.geom)
            LEFT JOIN 
                restricted_comunas rc ON cs.id = rc.comuna_id;
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("longitude", locationDTO.getLongitude())
                    .addParameter("latitude", locationDTO.getLatitude())
                    .executeAndFetchFirst(Boolean.class);
        }
    }
}
