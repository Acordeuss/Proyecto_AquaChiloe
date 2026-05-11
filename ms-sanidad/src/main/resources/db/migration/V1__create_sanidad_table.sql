CREATE TABLE tratamientos_sanitarios (
    id BIGSERIAL PRIMARY KEY,
    jaula_id BIGINT NOT NULL,
    medicamento VARCHAR(100) NOT NULL,
    fecha_aplicacion TIMESTAMP NOT NULL,
    dias_carencia INTEGER NOT NULL,
    veterinario_responsable VARCHAR(100),
    observaciones TEXT
);
