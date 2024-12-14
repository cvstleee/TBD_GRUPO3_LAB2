\c "e-commerce-db"

-- Carga de datos para la tabla "categories"
INSERT INTO categories (name) VALUES
('Electronica'),
('Hogar'),
('Libros'),
('Ropa'),
('Juguetes');

-- Carga de datos para la tabla "products"
INSERT INTO products (name, description, price, stock, state, category_id) VALUES
('Telefono inteligente', 'Telefono Android con 128GB de almacenamiento.', 399.99, 50, 'available', 1),
('Televisor LED', 'Televisor LED de 55 pulgadas, resolucion 4K.', 599.99, 20, 'available', 1),
('Aspiradora', 'Aspiradora con alta potencia y bolsa de almacenamiento.', 89.99, 15, 'available', 2),
('Sofa de 3 plazas', 'Sofa comodo de tela gris.', 299.99, 5, 'available', 2),
('El Quijote', 'Libro clasico de Miguel de Cervantes.', 15.99, 100, 'available', 3),
('Camiseta de algodon', 'Camiseta basica de algodon en color blanco.', 9.99, 200, 'available', 4),
('Muñeca de trapo', 'Muñeca suave para niñas pequeñas.', 12.99, 30, 'available', 5);

-- Carga de datos para la tabla "clients"
INSERT INTO clients (name, email, password, phone, latitude, longitude, location, deleted_at) VALUES
('Juan Pérez', 'juan.perez@example.com', 'admin', '+5691123456789', -33.435708, -70.629285, ST_SetSRID(ST_MakePoint(-70.629285, -33.435708), 4326), NULL),
('María López', 'maria.lopez@example.com', 'colocolo', '+6491167891234', -33.427909, -70.606867, ST_SetSRID(ST_MakePoint(-70.606867, -33.427909), 4326), NULL),
('Carlos García', 'carlos.garcia@example.com', 'contra', '+5691134567890', -33.561225, -70.571072, ST_SetSRID(ST_MakePoint(-70.571072, -33.561225), 4326), '2024-12-01 10:30:00');

-- Carga de datos para la table "distribuitors"
INSERT INTO distributors (name) VALUES
('Nicolas Perez'),	
('Angelica Guzman'),
('Anais Osorio');

-- Carga de datos para la tabla "orders"
INSERT INTO orders (order_date, state, client_id, distributor_id, total, shipping_date, latitude, longitude, delivery_location, deleted_at) VALUES
(NOW() - INTERVAL '2 months', 'shipped', 1, 1, 719.98, NOW() - INTERVAL '1 month', -33.435708, -70.629285, ST_SetSRID(ST_MakePoint(-70.629285, -33.435708), 4326), NULL),
(NOW() - INTERVAL '1 month', 'shipped', 2, 2, 35.98, NOW() - INTERVAL '15 days', -33.427909, -70.606867, ST_SetSRID(ST_MakePoint(-70.606867, -33.427909), 4326), NULL),
(NOW() - INTERVAL '3 weeks', 'pending', 3, 3, 49.99, NULL, -33.561225, -70.571072, ST_SetSRID(ST_MakePoint(-70.571072, -33.561225), 4326), NULL);

-- Carga de datos para la tabla "order_details"
INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES 
(1, 1, 1, 699.99),
(1, 5, 1, 49.99), 
(2, 3, 2, 19.99);

-- Carga de datos para la tabla "restricted_comunas"
INSERT INTO restricted_comunas (comuna_id) VALUES
(1),
(7),
(17);