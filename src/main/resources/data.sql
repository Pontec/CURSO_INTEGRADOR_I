CREATE DATABASE viacosta;
USE viacosta;

INSERT INTO empresa (razon_social, ruc, ciudad, departamento, pais, telefono)
VALUES ('VIA COSTA SAC', '20234578121', 'HUARAZ', 'ANCASH', 'PERU', '9563214789');

INSERT INTO sedes (nombre_sede, direccion, ciudad, departamento, pais, telefono, id_empresa)
VALUES ('Sede Central', 'Terminal Terrestre Chimbote','Chimbote', 'Ancash', 'Peru',
        '987654321',1),
       ('Sede Sur', 'Av. Secundaria 456','Arequipa', 'Arequipa', 'Peru', '123456789',1);

-- Insert data into the 'roles' table
INSERT INTO roles (nombre_rol)
VALUES ('ADMINISTRADOR'),
       ('VENTAS');

-- Insert data into the 'empleados' table
INSERT INTO empleados (nombre, apellido, dni, correo, contraseña, telefono, id_sede,estado)
VALUES ('Juan', 'Perez', '12345678', 'admin', '123', '987654321', 1,1),
       ('Maria', 'Lopez', '87654321', 'maria.lopez@example.com', 'password456', '123456789', 1,1),
       ('Andree', 'Bermudez', '45678912', 'andreebermudez@example.com', '123456', '987654321', 1,1);

-- Insert data into the 'empleado_roles' table
INSERT INTO empleado_roles (id_empleado, id_rol)
VALUES (1, 1),
       (2, 2),
       (3, 2);

INSERT INTO buses (marca, placa, modelo, capacidad_asientos, capacidad_carga, primer_piso, segundo_piso)
VALUES ('Volvo', 'ABC-123', 'Modelo X', 52, 1000.0,12,30),
       ('Mercedes', 'XYZ-789', 'Modelo Y', 52, 1200.0,12,30),
       ('Scania', 'DEF-456', 'Modelo Z', 52, 1500.0,12,30),
       ('Volvo', 'GHI-789', 'Modelo W', 52, 1000.0,12,30),
       ('Mercedes', 'JKL-012', 'Modelo V', 52, 1200.0,12,30),
       ('Scania', 'MNO-345', 'Modelo U', 52, 1500.0,12,30);

INSERT INTO tipo_asiento (nombre, descripcion, cargo_adicional)
VALUES ('VIP', 'Asiento VIP', 25.0),
       ('ECONOMICO', 'Asiento Económico', 0.0);

INSERT INTO rutas (origen, destino, duracion)
VALUES ('Chimbote', 'Lima', '10h'),
       ('Chimbote', 'Trujillo', '12h'),
       ('Chimbote', 'Huaraz', '8h');

-- Insert data into the 'asignacion_buses_rutas' table
INSERT INTO asignacion_buses_rutas (id_bus, id_ruta, fecha_salida, hora_salida, precio)
VALUES
    (1, 1, '2024-11-30', '08:00:00', 30),
    (2, 2, '2024-11-30', '09:00:00', 30),
    (3, 3, '2024-11-30', '10:00:00', 30),
    (4, 3, '2024-11-30', '12:00:00', 30),
    (5, 3, '2024-11-30', '16:00:00', 30),
    (6, 3, '2024-11-30', '22:00:00', 30),
    (1, 1, '2024-11-30', '15:00:00', 30),
    (1, 1, '2024-11-30', '20:00:00', 30),
    (1, 1, '2024-11-30', '22:00:00', 30),
    (3, 3, '2024-12-01', '10:00:00', 30),
    (4, 3, '2024-12-01', '12:00:00', 30),
    (5, 3, '2024-12-01', '16:00:00', 30),
    (6, 3, '2024-12-01', '22:00:00', 30),
    (1, 1, '2024-12-01', '15:00:00', 30),
    (1, 1, '2024-12-01', '20:00:00', 30),
    (1, 1, '2024-12-01', '22:00:00', 30),
    (3, 3, '2024-12-02', '10:00:00', 30),
    (4, 3, '2024-12-02', '12:00:00', 30),
    (5, 3, '2024-12-02', '16:00:00', 30),
    (6, 3, '2024-12-02', '22:00:00', 30),
    (1, 1, '2024-12-02', '15:00:00', 30),
    (1, 1, '2024-12-02', '20:00:00', 30),
    (1, 1, '2024-12-02', '22:00:00', 30);