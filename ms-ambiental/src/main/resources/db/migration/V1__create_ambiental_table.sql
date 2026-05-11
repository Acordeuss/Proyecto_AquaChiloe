CREATE TABLE lecturas_ambientales (
    id BIGSERIAL PRIMARY KEY,
    centro_id BIGINT NOT NULL,
    sensor_id VARCHAR(50) NOT NULL,
    oxigeno DOUBLE PRECISION NOT NULL,
    temperatura DOUBLE PRECISION NOT NULL,
    salinidad DOUBLE PRECISION NOT NULL,
    fecha_lectura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    alerta_critica BOOLEAN DEFAULT FALSE
);
