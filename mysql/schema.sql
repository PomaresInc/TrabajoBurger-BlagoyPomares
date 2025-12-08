DROP DATABASE IF EXISTS vegaburguer;
CREATE DATABASE vegaburguer;
USE vegaburguer;

CREATE TABLE categoria(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    image_path VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE dependiente (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    image_path VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE producto (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    image_path VARCHAR(255),
    description VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    categoriaId VARCHAR(36),

    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

CREATE TABLE pedido(
    id VARCHAR(36) PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    enregado BOOLEAN,
    client_name VARCHAR(100),
    dependienteId VARCHAR(36) NOT NULL,

    CONSTRAINT fk_pedido_dependiente FOREIGN KEY (dependienteId) REFERENCES dependiente(id)
);

CREATE TABLE lin_ped(
    id VARCHAR(36) PRIMARY KEY,
    unidades INT NOT NULL,
    price_unit DOUBLE NOT NULL,
    pedidoId VARCHAR(36) NOT NULL,
    productoId VARCHAR(36) NOT NULL,

    CONSTRAINT fk_linped_pedido FOREIGN KEY (pedidoId) REFERENCES pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_linped_producto FOREIGN KEY (productoId) REFERENCES producto(id)
);

-- Insertar datos de ejemplo

-- Categorías
INSERT INTO categoria (id, name, description, image_path, enabled) VALUES
('742c107a-eca5-48b0-b07c-965dc72c76d6', 'Hamburguesas', 'Deliciosas hamburguesas veganas', '742c107a-eca5-48b0-b07c-965dc72c76d6.jpg', TRUE);

-- Dependientes
INSERT INTO dependiente (id, name, email, password, image_path, enabled, is_admin) VALUES
('04f682a4-a272-49c2-be9b-b10ddc6eb6a9', 'Juan Pérez', 'juan.perez@vegaburguer.com', 'password123', '04f682a4-a272-49c2-be9b-b10ddc6eb6a9.jpg', TRUE, FALSE),
('TPV', 'Terminal Punto de Venta', 'tpv@vegaburguer.com', 'tpv123', 'tpv.jpg', TRUE, FALSE);

-- Productos
INSERT INTO producto (id, name, price, image_path, description, enabled, categoriaId) VALUES
('e0056557-dfac-4db2-933b-468b3f8becbb', 'Hamburguesa Clásica', 8.50, 'e0056557-dfac-4db2-933b-468b3f8becbb.jpg', 'Hamburguesa vegana con lechuga, tomate y cebolla', TRUE, '742c107a-eca5-48b0-b07c-965dc72c76d6');

-- Pedidos
INSERT INTO pedido (id, fecha, total, enregado, client_name, dependienteId) VALUES
('ped-001', '2024-12-07 10:30:00', 17.00, FALSE, 'María García', '04f682a4-a272-49c2-be9b-b10ddc6eb6a9');

-- Líneas de pedido
INSERT INTO lin_ped (id, unidades, price_unit, pedidoId, productoId) VALUES
('linped-001', 2, 8.50, 'ped-001', 'e0056557-dfac-4db2-933b-468b3f8becbb');


INSERT INTO dependiente (id, name, email, password, image_path, enabled, is_admin)
VALUES ('TPV', 'Terminal Punto de Venta', 'tpv@vegaburguer.com', 'tpv123', 'tpv.jpg', TRUE, FALSE)
ON DUPLICATE KEY UPDATE
    name = 'Terminal Punto de Venta',
    email = 'tpv@vegaburguer.com',
    enabled = TRUE;


-- Otorgar permisos al usuario federico desde cualquier host
GRANT ALL PRIVILEGES ON vegaburguer.* TO 'federico'@'%';
FLUSH PRIVILEGES;
