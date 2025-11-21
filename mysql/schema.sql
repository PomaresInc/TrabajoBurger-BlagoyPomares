DROP DATABASE IF EXISTS vegaburguer;
CREATE DATABASE vegaburguer;
USE vegaburguer;

CREATE TABLE categoria(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE dependiente (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    image_path VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    is_admin BOOLEAN NOT NULL );

CREATE TABLE producto (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    image_path VARCHAR(255),
    description VARCHAR(255),
    enabled BOOLEAN NOT NULL,
    categoriaId VARCHAR(36),

    CONSTRAINT fk_producto_categoria
    FOREIGN KEY (categoriaId) REFERENCES categoria(id)
);

CREATE TABLE pedidos(
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE lin_ped(
    id VARCHAR(36) PRIMARY KEY ,
    unidades INT NOT NULL
);