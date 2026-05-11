CREATE TABLE cargos (
    id BIGSERIAL PRIMARY KEY,
    nombre_cargo VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE trabajadores (
    id BIGSERIAL PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cargo_id BIGINT REFERENCES cargos(id),
    turno VARCHAR(50),
    activo BOOLEAN DEFAULT TRUE
);

INSERT INTO cargos (nombre_cargo, descripcion) VALUES ('Operario de Alimentación', 'Encargado de la logística de alimento');
INSERT INTO cargos (nombre_cargo, descripcion) VALUES ('Monitor Ambiental', 'Responsable de sensores y oxígeno');
