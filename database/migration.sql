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
DROP TABLE IF EXISTS comunas_santiago CASCADE;
DROP TABLE IF EXISTS restricted_comunas CASCADE;

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

CREATE TABLE distributors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
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
	distributor_id SERIAL NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    shipping_date timestamp without time zone,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    delivery_location GEOMETRY(POINT, 4326),
    deleted_at TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
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

CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    client_id INT, 
    table_name VARCHAR(100),
    element_id INT, 
    operation TEXT,
    description TEXT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE TABLE comunas_santiago (
    id SERIAL PRIMARY KEY,
    cod_comuna INT,
    comuna VARCHAR(255),
    provincia VARCHAR(255),
    region VARCHAR(255),
    geom GEOMETRY(POLYGON, 4326)
);

CREATE TABLE restricted_comunas (
    id SERIAL PRIMARY KEY,
    comuna_id INT NOT NULL,
    deleted_at TIMESTAMP,
    FOREIGN KEY (comuna_id) REFERENCES comunas_santiago(id)
);

CREATE INDEX idx_stores_location ON stores USING GIST(location);
CREATE INDEX idx_clients_location ON clients USING GIST(location);
CREATE INDEX idx_orders_delivery_location ON orders USING GIST(delivery_location);
CREATE INDEX idx_comunas_santiago_geom ON comunas_santiago USING GIST(geom);