-- Creación de la base de datos
CREATE DATABASE "e-commerce-db";

\c "e-commerce-db"

CREATE EXTENSION postgis;

DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS stores CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS clients CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS logs CASCADE;
DROP TABLE IF EXISTS municipalities CASCADE;

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMP
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    state VARCHAR(50) NOT NULL,
    category_id SERIAL NOT NULL,
    deleted_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE stores (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    location GEOMETRY(POINT, 4326)
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    location GEOMETRY(POINT, 4326),
    deleted_at TIMESTAMP
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    order_date TIMESTAMP NOT NULL,
    state VARCHAR(50) NOT NULL,
    client_id SERIAL NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    shipping_date timestamp without time zone,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    delivery_location GEOMETRY(POINT, 4326),
    deleted_at TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE order_details (
    id SERIAL PRIMARY KEY,
    order_id SERIAL NOT NULL,
    product_id SERIAL NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    deleted_at TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE municipalities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    restricted BOOLEAN DEFAULT FALSE,
    area GEOMETRY(MULTIPOLYGON, 4326)
);

CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    client_id INT, 
    table_name VARCHAR(100),
    element_id INT, 
    operation TEXT,
    description TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE INDEX idx_stores_location ON stores USING GIST(location);
CREATE INDEX idx_clients_location ON clients USING GIST(location);
CREATE INDEX idx_orders_delivery_location ON orders USING GIST(delivery_location);
CREATE INDEX idx_municipalities_area ON municipalities USING GIST(area);