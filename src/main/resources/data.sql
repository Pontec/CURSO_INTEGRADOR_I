CREATE DATABASE viacosta;

USE viacosta;

CREATE TABLE clientes (
                          id_cliente INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(50) NOT NULL,
                          apellido VARCHAR(50) NOT NULL,
                          dni VARCHAR(15) NOT NULL UNIQUE,
                          telefono VARCHAR(15),
                          correo VARCHAR(100),
                          direccion VARCHAR(100)
);


CREATE TABLE sedes (
                       id_sede INT PRIMARY KEY AUTO_INCREMENT,
                       nombre_sede VARCHAR(100) NOT NULL,
                       direccion VARCHAR(255) NOT NULL,
                       ruc VARCHAR(11) NOT NULL UNIQUE,
                       ciudad VARCHAR(100) NOT NULL,
                       departamento VARCHAR(100) NOT NULL,
                       pais VARCHAR(100) NOT NULL,
                       telefono VARCHAR(15)
);

CREATE TABLE empleados (
                           id_empleado INT PRIMARY KEY AUTO_INCREMENT,
                           dni VARCHAR(15) NOT NULL UNIQUE,
                           nombre VARCHAR(50) NOT NULL,
                           apellido VARCHAR(50) NOT NULL,
                           correo VARCHAR(100),
                           contraseña VARCHAR(100),
                           telefono VARCHAR(15),
                           id_sede INT NOT NULL,
                           FOREIGN KEY (id_sede) REFERENCES sedes(id_sede)
);

CREATE TABLE roles (
                       id_rol INT PRIMARY KEY AUTO_INCREMENT,
                       nombre_rol ENUM('ADMINISTRADOR', 'VENTAS') NOT NULL
);

CREATE TABLE empleado_roles (
                               id_empleado INT NOT NULL,
                               id_rol INT NOT NULL,
                               PRIMARY KEY (id_empleado, id_rol),
                               FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
                               FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);


CREATE TABLE rutas (
                       id_ruta INT PRIMARY KEY AUTO_INCREMENT,
                       origen VARCHAR(255) NOT NULL,
                       destino VARCHAR(255) NOT NULL,
                       duracion VARCHAR(10) NOT NULL
);

CREATE TABLE buses (
                       id_bus INT PRIMARY KEY AUTO_INCREMENT,
                       marca VARCHAR(255) NOT NULL,
                       placa VARCHAR(10) UNIQUE NOT NULL,
                       modelo VARCHAR(255) NOT NULL,
                       capacidad_asientos INT NOT NULL,
                       capacidad_carga DECIMAL(10, 2) NOT NULL
);

CREATE TABLE tipo_asiento (
                              id_tipo_asiento INT PRIMARY KEY AUTO_INCREMENT,
                              nombre VARCHAR(255) UNIQUE NOT NULL,
                              descripcion VARCHAR(255) UNIQUE NOT NULL,
                              cargo_adicional DECIMAL(10, 2) NOT NULL CHECK (cargo_adicional IN (0, 15))
);

CREATE TABLE asientos (
                          id_asiento INT PRIMARY KEY AUTO_INCREMENT,
                          id_bus INT NOT NULL,
                          numero_asiento INT NOT NULL,
                          estado ENUM('disponible', 'ocupado', 'reservado') NOT NULL DEFAULT 'disponible',
                          id_tipo_asiento INT NOT NULL,
                          precio DECIMAL(10, 2) NOT NULL,
                          FOREIGN KEY (id_bus) REFERENCES buses(id_bus),
                          FOREIGN KEY (id_tipo_asiento) REFERENCES tipo_asiento(id_tipo_asiento)
);

CREATE TABLE asignacion_buses_rutas (
                                        id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
                                        id_bus INT NOT NULL,
                                        id_ruta INT NOT NULL,
                                        fecha_salida DATE NOT NULL,
                                        hora_salida TIME NOT NULL,
                                        FOREIGN KEY (id_bus) REFERENCES buses(id_bus),
                                        FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta)
);

CREATE TABLE comprobantes (
                              id_comprobante INT PRIMARY KEY AUTO_INCREMENT,
                              tipo_comprobante ENUM('boleta', 'factura') NOT NULL,
                              numero_comprobante VARCHAR(20) UNIQUE NOT NULL,
                              fecha_emision DATE NOT NULL
);

CREATE TABLE compras (
                         id_compra INT PRIMARY KEY AUTO_INCREMENT,
                         id_cliente INT NOT NULL,
                         id_empleado INT NOT NULL,
                         tipo_compra ENUM('boleta', 'encomienda') NOT NULL,
                         fecha DATE NOT NULL,
                         hora TIME NOT NULL,
                         FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
                         FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);

CREATE TABLE detalle_boleta (
                                id_detalle INT PRIMARY KEY AUTO_INCREMENT,
                                id_comprobante INT NOT NULL,
                                id_asiento INT NOT NULL,
                                id_compra INT NOT NULL,
                                descripcion VARCHAR(255) NOT NULL,
                                fecha_viaje DATE NOT NULL,
                                hora_viaje TIME NOT NULL,
                                metodo_pago ENUM('efectivo', 'tarjeta') NOT NULL, -- Método de pago agregado
                                precio_unitario DECIMAL(10, 2) NOT NULL,
                                subtotal DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (id_comprobante) REFERENCES comprobantes(id_comprobante),
                                FOREIGN KEY (id_asiento) REFERENCES asientos(id_asiento),
                                FOREIGN KEY (id_compra) REFERENCES compras(id_compra)
);

CREATE TABLE detalle_encomienda (
                                    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
                                    id_comprobante INT NOT NULL,
                                    id_compra INT NOT NULL,
                                    id_bus INT NOT NULL,
                                    descripcion VARCHAR(255) NOT NULL,
                                    peso DECIMAL(5, 2) NOT NULL,
                                    metodo_pago ENUM('efectivo', 'tarjeta') NOT NULL, -- Método de pago agregado
                                    precio_unitario DECIMAL(10, 2) NOT NULL,
                                    subtotal DECIMAL(10, 2) NOT NULL,
                                    FOREIGN KEY (id_comprobante) REFERENCES comprobantes(id_comprobante),
                                    FOREIGN KEY (id_compra) REFERENCES compras(id_compra),
                                    FOREIGN KEY (id_bus) REFERENCES buses(id_bus)
);

USE viacosta;

-- Insertar la sede ViaCosta
INSERT INTO sedes (nombre_sede, direccion, ruc, ciudad, departamento, pais, telefono)
VALUES ('ViaCosta', '123 Main St', '12345678901', 'Lima', 'Lima', 'Peru', '987654321');

-- Insertar el empleado Andree Bermúdez
INSERT INTO empleados ( dni, nombre, apellido,correo, contraseña ,telefono,  id_sede)
VALUES ( '12345678', 'Andree', 'Bermúdez','user', "12345" ,'987654321',  1);

INSERT INTO roles (nombre_rol) VALUES ('ADMINISTRADOR');
INSERT INTO roles (nombre_rol) VALUES ('VENTAS');

INSERT INTO empleado_roles (id_empleado, id_rol) VALUES (1,1);
INSERT INTO empleado_roles (id_empleado, id_rol) VALUES (1,2);