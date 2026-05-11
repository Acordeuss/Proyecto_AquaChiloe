CREATE TABLE registro_biomasa (
    id_registro_biomasa BIGSERIAL PRIMARY KEY,
    id_jaula BIGINT NOT NULL,
    peso_promedio DOUBLE PRECISION NOT NULL,
    cantidad_peces INTEGER NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
