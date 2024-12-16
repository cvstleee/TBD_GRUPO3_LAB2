package com.example.backend.repositories;

import com.example.backend.dtos.LocationDTO;
import com.example.backend.entities.ComunaEntity;
import com.example.backend.entities.ProductEntity;
import com.example.backend.entities.RestrictedCommune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
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

    //    Restricted commune

    public RestrictedCommune postRestrictedCommune(int communeId) {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO restricted_comunas (comuna_id) "+ "VALUES (:comuna_id)";
            int id = con.createQuery(query, true)
                    .addParameter("comuna_id", communeId)
                    .executeUpdate()
                    .getKey(Integer.class);
            RestrictedCommune restrictedCommune = new RestrictedCommune();
            restrictedCommune.setId(id);
            restrictedCommune.setCommuneId(communeId);
            return restrictedCommune;
        }
    }

    public List<RestrictedCommune> getAllRestrictedCommune() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT id, comuna_Id AS communeId, deleted_at FROM restricted_comunas AS rc WHERE rc.deleted_at IS NULL")
                    .executeAndFetch(RestrictedCommune.class);
        }
    }

    public RestrictedCommune deleteRestrictedCommune(int communeId) {
        String query = "UPDATE restricted_comunas SET deleted_at = :deleted_at WHERE comuna_id = :comuna_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("deleted_at", LocalDateTime.now())
                    .addParameter("comuna_id", communeId)
                    .executeUpdate();
            RestrictedCommune restrictedCommune = new RestrictedCommune();
            restrictedCommune.setId(communeId);
            return restrictedCommune;
        }
    }

  
    public List<ComunaEntity> comunaNoRestricted(){

        String querry = """
               
                SELECT
                   cs.id,
                   cs.cod_comuna,
                   cs.comuna,
                   cs.provincia,
                   cs.region,
                   ST_AsGeoJSON(cs.geom) AS geom
                FROM
                   public.comunas_santiago cs
                WHERE
                   cs.id NOT IN (
                       SELECT comuna_id
                       FROM public.restricted_comunas
                       WHERE deleted_at IS NULL
                   );
                """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(querry).executeAndFetch(ComunaEntity.class);
        }
    }
}
