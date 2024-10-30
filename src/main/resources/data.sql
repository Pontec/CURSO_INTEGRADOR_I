CREATE
DATABASE viacosta;

USE
viacosta;

CREATE TABLE clientes
(
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre     VARCHAR(50) NOT NULL,
    apellido   VARCHAR(50) NOT NULL,
    dni        VARCHAR(15) NOT NULL UNIQUE,
    telefono   VARCHAR(15),
    correo     VARCHAR(100),
    direccion  VARCHAR(100)
);


CREATE TABLE sedes
(
    id_sede      INT PRIMARY KEY AUTO_INCREMENT,
    nombre_sede  VARCHAR(100) NOT NULL,
    direccion    VARCHAR(255) NOT NULL,
    ruc          VARCHAR(11)  NOT NULL UNIQUE,
    ciudad       VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    pais         VARCHAR(100) NOT NULL,
    telefono     VARCHAR(15)
);

CREATE TABLE empleados
(
    id_empleado INT PRIMARY KEY AUTO_INCREMENT,
    dni         VARCHAR(15) NOT NULL UNIQUE,
    nombre      VARCHAR(50) NOT NULL,
    apellido    VARCHAR(50) NOT NULL,
    correo      VARCHAR(100),
    contraseña  VARCHAR(100),
    telefono    VARCHAR(15),
    id_sede     INT         NOT NULL,
    FOREIGN KEY (id_sede) REFERENCES sedes (id_sede)
);

CREATE TABLE roles
(
    id_rol     INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol ENUM('ADMINISTRADOR', 'VENTAS') NOT NULL
);

CREATE TABLE empleado_roles
(
    id_empleado INT NOT NULL,
    id_rol      INT NOT NULL,
    PRIMARY KEY (id_empleado, id_rol),
    FOREIGN KEY (id_empleado) REFERENCES empleados (id_empleado),
    FOREIGN KEY (id_rol) REFERENCES roles (id_rol)
);


CREATE TABLE rutas
(
    id_ruta  INT PRIMARY KEY AUTO_INCREMENT,
    origen   VARCHAR(255) NOT NULL,
    destino  VARCHAR(255) NOT NULL,
    duracion VARCHAR(10)  NOT NULL
);

CREATE TABLE buses
(
    id_bus             INT PRIMARY KEY AUTO_INCREMENT,
    marca              VARCHAR(255)       NOT NULL,
    placa              VARCHAR(10) UNIQUE NOT NULL,
    modelo             VARCHAR(255)       NOT NULL,
    capacidad_asientos INT                NOT NULL,
    capacidad_carga    DECIMAL(10, 2)     NOT NULL
);

CREATE TABLE tipo_asiento
(
    id_tipo_asiento INT PRIMARY KEY AUTO_INCREMENT,
    nombre          VARCHAR(255) UNIQUE NOT NULL,
    descripcion     VARCHAR(255) UNIQUE NOT NULL,
    cargo_adicional DECIMAL(10, 2)      NOT NULL CHECK (cargo_adicional IN (0, 10, 15, 20, 25))
);

CREATE TABLE asientos
(
    id_asiento      INT PRIMARY KEY AUTO_INCREMENT,
    id_bus          INT            NOT NULL,
    numero_asiento  INT            NOT NULL,
    estado          ENUM('DISPONIBLE', 'OCUPADO') NOT NULL DEFAULT 'DISPONIBLE',
    id_tipo_asiento INT            NOT NULL,
    precio          DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_bus) REFERENCES buses (id_bus),
    FOREIGN KEY (id_tipo_asiento) REFERENCES tipo_asiento (id_tipo_asiento)
);

CREATE TABLE asignacion_buses_rutas
(
    id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
    id_bus        INT  NOT NULL,
    id_ruta       INT  NOT NULL,
    fecha_salida  DATE NOT NULL,
    hora_salida   TIME NOT NULL,
    FOREIGN KEY (id_bus) REFERENCES buses (id_bus),
    FOREIGN KEY (id_ruta) REFERENCES rutas (id_ruta)
);

CREATE TABLE comprobantes
(
    id_comprobante     INT PRIMARY KEY AUTO_INCREMENT,
    tipo_comprobante   ENUM('boleta', 'factura') NOT NULL,
    numero_comprobante VARCHAR(20) UNIQUE NOT NULL,
    fecha_emision      DATE               NOT NULL
);

CREATE TABLE compras
(
    id_compra   INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente  INT  NOT NULL,
    id_empleado INT  NOT NULL,
    tipo_compra ENUM('boleta', 'factura') NOT NULL,
    fecha       DATE NOT NULL,
    hora        TIME NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente),
    FOREIGN KEY (id_empleado) REFERENCES empleados (id_empleado)
);

CREATE TABLE detalle_boleta
(
    id_detalle      INT PRIMARY KEY AUTO_INCREMENT,
    id_comprobante  INT            NOT NULL,
    id_asiento      INT            NOT NULL,
    id_asignacion   INT            NOT NULL,
    id_compra       INT            NOT NULL,
    descripcion     VARCHAR(255)   NOT NULL,
    fecha_viaje     DATE           NOT NULL,
    hora_viaje      TIME           NOT NULL,
    metodo_pago     ENUM('efectivo', 'tarjeta') NOT NULL, -- Método de pago agregado
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal        DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_comprobante) REFERENCES comprobantes (id_comprobante),
    FOREIGN KEY (id_asiento) REFERENCES asientos (id_asiento),
    FOREIGN KEY (id_asignacion) REFERENCES asignacion_buses_rutas (id_asignacion),
    FOREIGN KEY (id_compra) REFERENCES compras (id_compra)
);

CREATE TABLE detalle_encomienda
(
    id_detalle      INT PRIMARY KEY AUTO_INCREMENT,
    id_comprobante  INT            NOT NULL,
    id_compra       INT            NOT NULL,
    id_bus          INT            NOT NULL,
    descripcion     VARCHAR(255)   NOT NULL,
    peso            DECIMAL(5, 2)  NOT NULL,
    metodo_pago     ENUM('efectivo', 'tarjeta') NOT NULL, -- Método de pago agregado
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal        DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_comprobante) REFERENCES comprobantes (id_comprobante),
    FOREIGN KEY (id_compra) REFERENCES compras (id_compra),
    FOREIGN KEY (id_bus) REFERENCES buses (id_bus)
);

USE
viacosta;

-- Insert data into the 'sedes' table
INSERT INTO sedes (nombre_sede, direccion, ruc, ciudad, departamento, pais, telefono)
VALUES ('Sede Central', 'Terminal Terrestre Chimbote, Chimbote 02804', '123456789', 'Chimbote', 'Ancash', 'Peru',
        '987654321'),
       ('Sede Sur', 'Av. Secundaria 456', '987654321', 'Arequipa', 'Arequipa', 'Peru', '123456789');

-- Insert data into the 'roles' table
INSERT INTO roles (nombre_rol)
VALUES ('ADMINISTRADOR'),
       ('VENTAS');

-- Insert data into the 'empleados' table
INSERT INTO empleados (nombre, apellido, dni, correo, contraseña, telefono, id_sede,estado)
VALUES ('Juan', 'Perez', '12345678', 'admin', '123', '987654321', 1),
       ('Maria', 'Lopez', '87654321', 'maria.lopez@example.com', 'password456', '123456789', 1),
       ('Andree', 'Bermudez', '45678912', 'andreebermudez@example.com', '123456', '987654321', 1);

-- Insert data into the 'empleado_roles' table
INSERT INTO empleado_roles (id_empleado, id_rol)
VALUES (1, 1),
       (2, 2),
       (3, 2);

-- Insert data into the 'clientes' table
INSERT INTO clientes (nombre, apellido, dni, correo, telefono, direccion)
VALUES ('Carlos', 'Gomez', '11223344', 'carlos.gomez@example.com', '987654321', 'Calle Falsa 123'),
       ('Ana', 'Martinez', '44332211', 'ana.martinez@example.com', '123456789', 'Calle Verdadera 456'),
       ('Pedro', 'Perez', '22334455', 'pedro.perez@example.com', '987654321', 'Calle Falsa 123'),
       ('Luis', 'Garcia', '33445566', 'luis.garcia@example.com', '123456789', 'Calle Verdadera 456');

-- Insert data into the 'buses' table
INSERT INTO buses (marca, placa, modelo, capacidad_asientos, capacidad_carga)
VALUES ('Volvo', 'ABC-123', 'Modelo X', 52, 1000.0),
       ('Mercedes', 'XYZ-789', 'Modelo Y', 52, 1200.0),
       ('Scania', 'DEF-456', 'Modelo Z', 52, 1500.0),
       ('Volvo', 'GHI-789', 'Modelo W', 52, 1000.0),
       ('Mercedes', 'JKL-012', 'Modelo V', 52, 1200.0),
       ('Scania', 'MNO-345', 'Modelo U', 52, 1500.0);

-- Insert data into the 'tipo_asiento' table
INSERT INTO tipo_asiento (nombre, descripcion, cargo_adicional)
VALUES ('VIP', 'Asiento VIP', 25.0),
       ('ECONOMICO', 'Asiento Económico', 0.0);

-- Insert data into the 'asientos' table
INSERT INTO asientos (id_bus, numero_asiento, estado, precio, id_tipo_asiento)
VALUES (1, 1, 'DISPONIBLE', 100.0, 1),
       (1, 2, 'DISPONIBLE', 100.0, 1),
       (1, 3, 'DISPONIBLE', 50.0, 2),
       (2, 1, 'DISPONIBLE', 120.0, 1),
       (2, 2, 'DISPONIBLE', 120.0, 1),
       (2, 3, 'DISPONIBLE', 60.0, 2);

-- Insert data into the 'rutas' table
INSERT INTO rutas (origen, destino, duracion)
VALUES ('Chimbote', 'Lima', '10h'),
       ('Chimbote', 'Trujillo', '12h'),
       ('Chimbote', 'Huaraz', '8h');


-- Insert data into the 'asignacion_buses_rutas' table
INSERT INTO asignacion_buses_rutas (id_bus, id_ruta, fecha_salida, hora_salida)
VALUES (1, 1, '2024-10-01', '08:00:00'),
       (2, 2, '2024-10-02', '09:00:00'),
       (3, 3, '2024-10-30', '10:00:00'),
       (4, 3, '2024-10-30', '12:00:00'),
       (5, 3, '2024-10-30', '16:00:00'),
       (6, 3, '2024-10-30', '22:00:00');

-- Insert data into the 'compras' table
INSERT INTO compras (id_cliente, id_empleado, tipo_compra, fecha, hora)
VALUES (1, 2, 'boleta', '2023-10-01', '10:00:00'),
       (2, 2, 'boleta', '2023-10-02', '11:00:00'),
       (3, 2, 'boleta', '2024-10-30', '12:00:00'),
       (4, 2, 'boleta', '2024-10-30', '16:00:00'),
       (5, 2, 'boleta', '2024-10-30', '16:00:00');


-- Insert data into the 'comprobantes' table
INSERT INTO comprobantes (tipo_comprobante, numero_comprobante, fecha_emision)
VALUES ('boleta', 12345, '2023-10-01'),
       ('boleta', 67890, '2023-10-02'),
       ('boleta', 13579, '2024-10-30'),
       ('boleta', 24680, '2024-10-30'),
         ('boleta', 35791, '2024-10-30');

-- Insert data into the 'detalle_boleta' table
INSERT INTO detalle_boleta (descripcion, fecha_viaje, hora_viaje, metodo_pago, precio_unitario, subtotal,
                            id_comprobante, id_asiento, id_compra, id_asignacion)
VALUES ('Viaje Chimbote-Arequipa', '2023-10-01', '08:00:00', 'tarjeta', 60.0, 60.0, 1, 1, 1, 1),
       ('Viaje Chimbote-Cusco', '2023-10-02', '09:00:00', 'efectivo', 60.0, 60.0, 2, 4, 2, 2),
         ('Viaje Chimbote-Huaraz', '2024-10-30', '10:00:00', 'tarjeta', 50.0, 50.0, 3, 3, 3, 3),
         ('Viaje Chimbote-Huaraz', '2024-10-30', '16:00:00', 'efectivo', 60.0, 60.0, 4, 6, 4, 4),
         ('Viaje Chimbote-Huaraz', '2024-10-30', '16:00:00', 'efectivo', 60.0, 60.0, 5, 6, 5, 5);

-- Insert data into the 'detalle_encomienda' table
INSERT INTO detalle_encomienda (descripcion, peso, metodo_pago, precio_unitario, subtotal, id_bus, id_comprobante,
                                id_compra)
VALUES ('Encomienda 1', 10.0, 'tarjeta', 50, 50, 1, 1, 1),
       ('Encomienda 2', 20.0, 'efectivo', 100, 100, 2, 2, 2);