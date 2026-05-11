CREATE TABLE registros_alimentacion (
    id BIGSERIAL PRIMARY KEY,
    jaula_id BIGINT NOT NULL,
    cantidad_alimento_kilos DOUBLE PRECISION NOT NULL,
    tipo_alimento VARCHAR(100),
    fecha_alimentacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    es_valido BOOLEAN DEFAULT TRUE
);
