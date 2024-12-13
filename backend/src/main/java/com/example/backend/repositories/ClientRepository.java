package com.example.backend.repositories;

import com.example.backend.dtos.RegisterDTO;
import com.example.backend.entities.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Repository
public class ClientRepository {
    @Autowired
    private Sql2o sql2o;

    public List<ClientEntity> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE deleted_at IS NULL")
                    .executeAndFetch(ClientEntity.class);
        }
    }

    public ClientEntity findById(long id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE id = :id AND deleted_at IS NULL")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    public ClientEntity findByEmail(String email) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE email = :email AND deleted_at IS NULL")
                    .addParameter("email", email)
                    .executeAndFetchFirst((ClientEntity.class));
        }
    }

    public ClientEntity findByEmailAndNotId(String email, long id) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM clients WHERE email = :email AND id != :id AND deleted_at IS NULL";
            return con.createQuery(sql)
                    .addParameter("email", email)
                    .addParameter("id", id)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    public ClientEntity findByPhone(String phone) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM clients WHERE phone = :phone AND deleted_at IS NULL")
                    .addParameter("phone", phone)
                    .executeAndFetchFirst(ClientEntity.class);
        }
    }

    public ClientEntity save(RegisterDTO registerDTO) {
        String query = "INSERT INTO clients (name, email, password, phone, latitude, longitude, location) " +
                "VALUES (:name, :email, :password, :phone, :latitude, :longitude, ST_GeomFromText(:location, 4326)) " +
                "RETURNING id";
        
        String locationWKT = String.format(Locale.US, "POINT(%f %f)", registerDTO.getLongitude(), registerDTO.getLatitude());

        try (Connection con = sql2o.open()) {
            int id = con.createQuery(query, true)
                    .addParameter("name", registerDTO.getName())
                    .addParameter("email", registerDTO.getEmail())
                    .addParameter("password", registerDTO.getPassword())
                    .addParameter("phone", registerDTO.getPhone())
                    .addParameter("latitude", registerDTO.getLatitude())
                    .addParameter("longitude", registerDTO.getLongitude())
                    .addParameter("location", locationWKT)
                    .executeUpdate()
                    .getKey(Integer.class);

            return findById(id);
        }
    }


    public ClientEntity update(long id, ClientEntity clientEntity) {
        String query = "UPDATE clients SET email = :email WHERE id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .addParameter("email", clientEntity.getEmail())
                    .addParameter("id", id)
                    .executeUpdate();

            ClientEntity updatedClientEntity = con.createQuery("SELECT * FROM clients WHERE id = :id")
                    .addParameter("id", clientEntity.getId())
                    .executeAndFetchFirst(ClientEntity.class);

            return updatedClientEntity;
        }
    }

    public boolean delete(long id) {
        String query = "UPDATE clients SET deleted_at = :deleted_at WHERE id = :id AND deleted_at IS NULL";

        try (Connection con = sql2o.open()) {
            int rowsSoftDeleted = con.createQuery(query)
                    .addParameter("deleted_at", LocalDateTime.now())
                    .addParameter("id", id)
                    .executeUpdate()
                    .getResult();
            return rowsSoftDeleted > 0;
        }
    }
}
